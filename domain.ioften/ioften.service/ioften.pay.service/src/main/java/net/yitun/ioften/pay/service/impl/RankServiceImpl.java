package net.yitun.ioften.pay.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;

import net.yitun.basic.model.Result;
import net.yitun.basic.utils.DateUtil;
import net.yitun.basic.utils.IdUtil;
import net.yitun.ioften.crm.UserApi;
import net.yitun.ioften.pay.conf.Constant;
import net.yitun.ioften.pay.entity.Rank;
import net.yitun.ioften.pay.entity.Wallet;
import net.yitun.ioften.pay.entity.vo.rank.MyRankVo;
import net.yitun.ioften.pay.entity.vo.rank.RankQueryVo;
import net.yitun.ioften.pay.model.rank.RankQuery;
import net.yitun.ioften.pay.model.wallet.WalletQuery;
import net.yitun.ioften.pay.repository.RankRepository;
import net.yitun.ioften.pay.service.RankService;
import net.yitun.ioften.pay.service.WalletService;

@Service("pay.RankService")
@SuppressWarnings("serial")
@CacheConfig(cacheNames = Constant.CKEY_RANK)
public class RankServiceImpl implements RankService {

    @Autowired
    protected UserApi userApi;

    @Autowired
    protected WalletService walletService;

    @Autowired
    protected RankRepository repository;

    @Override
    @Cacheable(key = "#uid")
    public MyRankVo my(Long uid) {
        Date nowTime = new Date(System.currentTimeMillis());
        Date etime = DateUtil.dayOfEndTime(nowTime);
        Date stime = DateUtil.dayOfStartTime(nowTime);

        MyRankVo rankVo = this.repository.my(uid, stime, etime);
        rankVo.setNo(null != rankVo.getNo() && 100 < rankVo.getNo() ? -1 : rankVo.getNo());

        return rankVo;
    }

    @Override
    @Cacheable(key = "#query.pageNo+'-'+#query.pageSize")
    public Page<Rank> query(RankQuery query) {
        RankQueryVo queryVo //
                = new RankQueryVo(query.getStime(), query.getEtime());
        Page<Rank> ranks = this.repository.query(queryVo);

        // 附加用户的简要信息
        ranks.stream().forEach(rank -> this.userApi.nick(rank.getUid()).getData());
        return ranks;
    }

    @Override
    @CacheEvict(allEntries = true, beforeInvocation = false)
    public Result<Integer> etlRank() {
        WalletQuery query = new WalletQuery() {
            {
                setPageNo(1);
                setPageSize(10000);
                setSortBy("t1.total_income desc, t1.id desc");
            }
        };
        Page<Wallet> page = this.walletService.query(query);

        int index = 1;
        List<Rank> ranks = new ArrayList<Rank>(page.size());
        Date nowTime = new Date(System.currentTimeMillis());
        for (Wallet wallet : page)
            ranks.add(new Rank(IdUtil.id(), wallet.getId(), index++, wallet.getTotalIncome(), nowTime, nowTime));

        int total = this.repository.imports(ranks);

        return new Result<>(total);
    }

}
