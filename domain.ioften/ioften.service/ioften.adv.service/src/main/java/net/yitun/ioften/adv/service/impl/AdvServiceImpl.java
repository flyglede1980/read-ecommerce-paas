package net.yitun.ioften.adv.service.impl;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;

import lombok.extern.slf4j.Slf4j;
import net.yitun.basic.Code;
import net.yitun.basic.cache.utils.IocUtil;
import net.yitun.basic.dict.Status;
import net.yitun.basic.exp.BizException;
import net.yitun.basic.model.Result;
import net.yitun.basic.model.vo.DeleteVo;
import net.yitun.basic.security.SecurityUtil;
import net.yitun.basic.utils.IdUtil;
import net.yitun.basic.utils.JsonUtil;
import net.yitun.basic.utils.SetUtil;
import net.yitun.comon.CobjsApi;
import net.yitun.comon.model.cobj.Cobj;
import net.yitun.ioften.adv.conf.Constant;
import net.yitun.ioften.adv.entity.Adv;
import net.yitun.ioften.adv.entity.vo.AdvQueryVo;
import net.yitun.ioften.adv.model.adv.AdvCreate;
import net.yitun.ioften.adv.model.adv.AdvModify;
import net.yitun.ioften.adv.model.adv.AdvQuery;
import net.yitun.ioften.adv.repository.AdvRepository;
import net.yitun.ioften.adv.service.AdvService;

@Slf4j
@Service("adv.AdvService")
@CacheConfig(cacheNames = Constant.CK_ADV)
public class AdvServiceImpl implements AdvService {

    @Autowired
    protected CobjsApi cobjsApi;

    @Autowired
    protected AdvRepository repository;

    @Override
    @Cacheable(key = "#id")
    public Adv get(Long id) {
        return this.repository.get(id);
    }

    @Override
    public Page<Adv> query(AdvQuery query) {
        AdvQueryVo queryVo //
                = new AdvQueryVo(query.getId(), query.getTitle(), query.getShowBtn(), query.getSeat(), query.getAction(),
                        query.getStatus());
        return this.repository.query(queryVo);
    }

    @Override
    @Transactional
    public Result<?> create(AdvCreate model) {
        long id = IdUtil.id();
        Set<Cobj> cobjs = null;
        Set<String> covers = model.getCover(), _covers = null;
        if (SetUtil.isNotEmpty(covers)) { // 新上传的文件进行路径规划
            cobjs = new LinkedHashSet<>(covers.size());
            _covers = new LinkedHashSet<>(covers.size());
            long index = System.currentTimeMillis();
            String newPath = "adv/" + id + "/", newKey = null; // 归档新路径: adv/id/0
            for (String cover : covers) {
                if (StringUtils.isNoneBlank(cover)) {
                    newKey = newPath + (++index);
                    _covers.add(newKey);
                    cobjs.add(new Cobj(cover, newKey));
                }
            }
        }

        // 生成数据库新记录并保存
        Date nowTime = new Date(System.currentTimeMillis());
        Adv adv = new Adv(id, model.getTitle(), SetUtil.toString(_covers), model.getSeat(), model.getAction(),
                model.getShowBtn(), model.getActionConf(), model.getSequence(), model.getRemark(), Status.ENABLE // 默认启用
                , nowTime, nowTime);
        if (!this.repository.create(adv))
            return new Result<>(Code.BIZ_ERROR, "广告保存失败");

        // 如果广告中有涉及到资源文件，需要进行固话处理
        if (SetUtil.isNotEmpty(cobjs)) {
            Result<?> result = null;
            if (null == (result = this.cobjsApi.sure(cobjs)) || !result.ok()) {
                if (log.isInfoEnabled())
                    log.info("<<< {}.create() sure cobj faild, {}", this.getClass().getName(), JsonUtil.toJson(result));
                throw new BizException(Code.BIZ_ERROR, "广告图片保存失败");
            }
        }

        return Result.OK;
    }

    @Override
    @Transactional
    @CacheEvict(key = "#model.id")
    public Result<?> modify(AdvModify model) {
        Adv adv = null;
        Long id = model.getId();
        if (null == (adv = this.repository.lock(id)))
            throw new BizException(Code.NOT_FOUND, "该广告不存在");

        // TODO 获取原有的封面图片
        System.out.println(adv);

        return null;
    }

    @Override
    @Transactional
    @Caching(evict = { //
            @CacheEvict(allEntries = true), // 删除后将所有广告全部删除
            @CacheEvict(allEntries = true, value = Constant.CK_VIEW) }) // 广告排版在任何广告移除时也需要重新排版
    public Result<?> delete(Set<Long> ids) {
        AdvService service // 需要借助Cache AOP减少DB IO
                = IocUtil.getBean(AdvService.class);
        ids.stream() //
                .forEach(id -> {
                    Adv adv = service.get(id);
                    if (null != adv) { // 获取广告详细，需要回收OBS存储资源
                        Set<String> covers = SetUtil.split(adv.getCover());
                        if (null != covers && 0 != covers.size()) {
                            Result<?> result = this.cobjsApi.delete(covers);
                            log.info("<<< {}.delete() recycle conver id:{} cover:{}", this.getClass().getName(), id,
                                    JsonUtil.toJson(result));
                        }
                    }
                });

        DeleteVo delVo //
                = new DeleteVo(ids, new Date(System.currentTimeMillis()));

        int size = this.repository.delete(delVo);
        if (log.isInfoEnabled())
            log.info("<<< {}.delete() uid:{} ids:{} size:{}", this.getClass().getName(), SecurityUtil.getId(),
                    JsonUtil.toJson(ids), size);

        return Result.OK;
    }

}
