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
import net.yitun.basic.model.Result;
import net.yitun.basic.model.vo.DeleteVo;
import net.yitun.basic.utils.IdUtil;
import net.yitun.basic.utils.JsonUtil;
import net.yitun.basic.utils.SetUtil;
import net.yitun.ioften.cms.ShowApi;
import net.yitun.ioften.cms.conf.Constant;
import net.yitun.ioften.cms.entity.Favorite;
import net.yitun.ioften.cms.entity.vo.FavoriteQueryVo;
import net.yitun.ioften.cms.model.favorite.FavoriteQuery;
import net.yitun.ioften.cms.model.show.NewsList;
import net.yitun.ioften.cms.repository.FavoriteRepository;
import net.yitun.ioften.cms.service.FavoriteService;

@Slf4j
@Service("cms.FavoriteService")
@CacheConfig(cacheNames = Constant.CK_FAVORITE)
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    protected ShowApi showApi;

    @Autowired
    protected FavoriteRepository repository;

    @Override
    @Cacheable(key = "#uid+'.'+#aid")
    public Favorite find(Long uid, Long aid) {
        return this.repository.find(uid, aid);
    }

    @Override
    public Page<Favorite> query(FavoriteQuery query) {
        FavoriteQueryVo queryVo //
                = new FavoriteQueryVo(query.getId(), query.getUid(), query.getAid(), null, null);
        Page<Favorite> page = this.repository.query(queryVo);

        int size = 0;
        if (0 != (size = page.size())) {
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
    @CacheEvict(key = "#uid+'.'+#aid")
    public Result<Boolean> collect(Long uid, Long aid) {
        Favorite favorite = null;
        // 存在记录时，自动切换到移除收藏
        if (null != (favorite = this.repository.lockFind(uid, aid))) {
            this.delete(uid, SetUtil.asSet(favorite.getId()));
            return new Result<>(false);
        }

        // 不存在收藏记录时，需要新构建
        Date nowtime = new Date(System.currentTimeMillis());
        favorite = new Favorite(IdUtil.id(), uid, aid, nowtime, nowtime);
        if (!this.repository.create(favorite))
            return new Result<>(Code.BIZ_ERROR, "收藏资讯保存失败");

        return new Result<>(true);
    }

    @Override
    @Transactional
    @CacheEvict(allEntries = true, beforeInvocation = false) // TODO 待实现只清除当前用户
    public Result<?> delete(Long uid, Set<Long> ids) {
        DeleteVo delVo //
                = new DeleteVo(ids, uid, new Date(System.currentTimeMillis()));
        int size = this.repository.delete(delVo);

        if (log.isInfoEnabled())
            log.info("<<< {}.delete() uid:{} ids:{} size:{}", this.getClass().getName(), uid, JsonUtil.toJson(ids), size);

        return Result.OK;
    }

}
