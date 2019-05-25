package net.yitun.ioften.cms.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;

import lombok.extern.slf4j.Slf4j;
import net.yitun.basic.Code;
import net.yitun.basic.cache.support.locker.RedisLock;
import net.yitun.basic.dict.Status;
import net.yitun.basic.dict.YesNo;
import net.yitun.basic.exp.BizException;
import net.yitun.basic.model.Result;
import net.yitun.basic.security.SecurityUtil;
import net.yitun.basic.utils.IdUtil;
import net.yitun.basic.utils.JsonUtil;
import net.yitun.ioften.Roles;
import net.yitun.ioften.cms.ShowApi;
import net.yitun.ioften.cms.conf.Constant;
import net.yitun.ioften.cms.entity.Browse;
import net.yitun.ioften.cms.entity.vo.browse.BrowseQueryVo;
import net.yitun.ioften.cms.entity.vo.browse.DeleteVo;
import net.yitun.ioften.cms.model.browse.BrowseQuery;
import net.yitun.ioften.cms.model.show.NewsList;
import net.yitun.ioften.cms.repository.BrowseRepository;
import net.yitun.ioften.cms.service.BrowseService;
import net.yitun.ioften.pay.dicts.Way;

@Slf4j
@Service(value = "cms.BrowseService")
@CacheConfig(cacheNames = Constant.CK_BROWSE)
public class BrowseServiceImpl implements BrowseService {

    @Autowired
    protected RedisLock lock;

    @Autowired
    protected ShowApi showApi;

    @Autowired
    protected BrowseRepository repository;

    @Override
    @Cacheable(key = "#uid+'.'+#aid")
    public Browse get(Long uid, Long aid) {
        return this.repository.get(uid, aid);
    }

    @Override
    public boolean lock(String uid, String aid, Long expire) {
        return this.lock.lock(uid, aid, expire);
    }

    @Override
    @Transactional
    @Cacheable(key = "#uid+'.'+#aid")
    public Browse find(Long uid, Long aid) {
        // 限制并发创建浏览记录
        if (!this.lock(uid.toString(), aid.toString(), 850L))
            throw new BizException(Code.BIZ_ERROR, "您操作太频繁");

        Browse browse = null;
        // 当用户还未浏览对应文章时，先创建一条浏览记录
        if (null != (browse = this.repository.lock(uid, aid)))
            return browse;

        // 新增浏览记录进行持久化，保存失败则默认返回无浏览记录
        Date nowTime = new Date(System.currentTimeMillis());
        browse = new Browse(IdUtil.id(), uid, aid, YesNo.NO /* view */ , YesNo.NO /* enjoy */, 0L /* award */,
                0L /* viewAward */, 0L /* enjoyAward */, 0L /* reward */, Status.ENABLE, nowTime, nowTime);
        if (!this.repository.create(browse))
            return null;

        return browse;
    }

    @Override
    public Page<Browse> query(BrowseQuery query) {
        Status status //
                = SecurityUtil.hasAnyRole(Roles.USER.name()) ? Status.ENABLE : null;
        BrowseQueryVo queryVo //
                = new BrowseQueryVo(null, query.getUid(), null, status);
        Page<Browse> page = this.repository.query(queryVo);

        int size = 0;
        if (null != page && 0 != (size = page.size())) {
            // 搜集所有的文章ID，便于批量获取文章信息
            Set<Long> ids = new HashSet<>(size);
            page.stream().forEach(item -> ids.add(item.getAid()));

            final Map<Long, NewsList> news;
            // 将文章的列表模式的信息绑定到浏览记录上
            Result<Map<Long, NewsList>> result = this.showApi.basics(ids);
            if (null != result && null != (news = result.getData()) && 0 != news.size())
                page.stream().forEach(item -> item.setNews(news.get(item.getAid())));
        }

        return page;
    }

    @Override
    @Transactional
    @CacheEvict(key = "#uid+'.'+#aid", beforeInvocation = false)
    public Browse modify(Long uid, Long aid, Way way, Long award) {
        Browse browse = null;
        if (null == (browse = this.repository.lock(uid, aid)))
            throw new BizException(Code.BIZ_ERROR, "浏览记录不存在");

        // 记录存在时，需要进行修改处理
        award = null == award ? 0L : award;
        browse.setAward(award + browse.getAward());
        browse.setMtime(new Date(System.currentTimeMillis()));

        if (way == Way.VIEW) { // 更新阅读标记和阅读挖矿数量
            browse.setView(YesNo.YES);
            browse.setViewAward(award + browse.getViewAward());

        } else if (way == Way.ENJOY) { // 点赞阅读标记和点赞挖矿数量
            browse.setEnjoy(YesNo.YES);
            browse.setEnjoyTime(browse.getMtime());
            browse.setEnjoyAward(award + browse.getEnjoyAward());
        }

        browse.setStatus(Status.ENABLE);
        if (!this.repository.modify(browse))
            throw new BizException(Code.BIZ_ERROR, "浏览记录更新失败");

        // TODO 同步通知文章修改其点赞、浏览统计数量

        return browse;
    }

    @Override
    @Transactional
    public Result<?> delete(Long uid, Set<Long> ids) {
        DeleteVo delVo //
                = new DeleteVo(uid, ids, Status.DISABLE, new Date(System.currentTimeMillis()));
        int size = this.repository.delete(delVo);

        if (log.isInfoEnabled())
            log.info("<<< {}.delete() uid:{} ids:{} size:{}", this.getClass().getName(), uid, JsonUtil.toJson(ids), size);

        return Result.OK;
    }

}
