package net.yitun.ioften.pay.website.action;

import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.yitun.basic.model.PageResult;
import net.yitun.basic.model.Result;
import net.yitun.basic.security.SecurityUtil;
import net.yitun.basic.utils.DateUtil;
import net.yitun.ioften.pay.RankApi;
import net.yitun.ioften.pay.entity.Rank;
import net.yitun.ioften.pay.entity.vo.rank.MyRankVo;
import net.yitun.ioften.pay.model.rank.MyRank;
import net.yitun.ioften.pay.model.rank.RankDetail;
import net.yitun.ioften.pay.model.rank.RankQuery;
import net.yitun.ioften.pay.service.RankService;

@Api(tags = "支付 服豆排名")
@RestController("pay.RankApi")
public class RankAction implements RankApi {

    @Autowired
    protected RankService service;

    @Override
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(value = "我的排名")
    public Result<MyRank> my() {
        Long uid = SecurityUtil.getId();
        MyRankVo myRank = this.service.my(uid);
        return new Result<>(new MyRank(myRank.getNo(), myRank.getRatio(), myRank.getMtime()));
    }

    @Override
    @ApiOperation(value = "分页查询", notes = "pageSize可取值: 3、10、20, 分页查询最多能查询前排名前100")
    public PageResult<RankDetail> query(RankQuery query) {
        // 100条后不再返回数据
        if (100 < query.getPageNo() * query.getPageSize())
            return new PageResult<>(100L, query.getPageNo(), Collections.emptyList());

        Date nowTime = new Date(System.currentTimeMillis());
        query.setEtime(DateUtil.dayOfEndTime(nowTime));
        query.setStime(DateUtil.dayOfStartTime(nowTime));

        Page<Rank> page = this.service.query(query);
        return new PageResult<>(page.getTotal(), page.getPages(),
                page.stream().map(rank -> new RankDetail(rank.getId(), rank.getUid(), rank.getNo(), rank.getAmount(),
                        rank.getCtime(), rank.getMtime(), rank.getUser())).collect(Collectors.toList()));
    }
}
