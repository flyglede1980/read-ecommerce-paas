package net.yitun.ioften.adv.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;

import net.yitun.basic.dict.Status;
import net.yitun.basic.utils.SetUtil;
import net.yitun.ioften.adv.conf.Constant;
import net.yitun.ioften.adv.dicts.Seat;
import net.yitun.ioften.adv.entity.Adv;
import net.yitun.ioften.adv.model.adv.AdvDetail;
import net.yitun.ioften.adv.model.adv.AdvQuery;
import net.yitun.ioften.adv.service.AdvService;
import net.yitun.ioften.adv.service.ConfService;
import net.yitun.ioften.adv.service.ShowService;

@Service("adv.ShowService")
@SuppressWarnings("unchecked")
@CacheConfig(cacheNames = Constant.CK_VIEW)
public class ShowServiceImpl implements ShowService {

    @Autowired
    protected ConfService conf;

    @Autowired
    protected AdvService advService;

    @Override
    @Cacheable(key = "'flash'")
    public AdvDetail flash() {
        if (!this.conf.isFlashAdv())
            return null;

        // 最多显示1条 startup 广告
        List<AdvDetail> advs = this.loadAdvs(1, Seat.FLASH);
        if (null == advs || advs.isEmpty())
            return null;

        return advs.get(0);
    }

    @Override
    @Cacheable(key = "'banner'")
    public List<AdvDetail> banner() {
        if (!this.conf.isBannerAdv())
            return Collections.EMPTY_LIST;

        // TODO 引入广告计划机制, 识别用户关键信息就行匹配

        // 查询当前banner的广告列表

        // 最多显示16条 banner 广告
        return this.loadAdvs(16, Seat.BANNER);
    }

    protected List<AdvDetail> loadAdvs(int szie, Seat seat) {
        AdvQuery query //
                = new AdvQuery(null, null, null, SetUtil.asSet(seat), null, SetUtil.asSet(Status.ENABLE));
        query.setPageSize(szie);
        query.setSortBy("t1.sequence asc, t1.mtime desc"); // 按照顺序号升序、修改时间降序，ID降序
        Page<Adv> page = this.advService.query(query);

        // 重新组装广告信息，减掉非必要属性
        List<AdvDetail> advs = new ArrayList<>(page.size());
        for (Adv adv : page)
            advs.add(new AdvDetail(adv.getId(), adv.getTitle(), adv.getCover(), adv.getAction(), adv.getShowBtn(),
                    adv.getActionConf()));

        return advs;
    }

}
