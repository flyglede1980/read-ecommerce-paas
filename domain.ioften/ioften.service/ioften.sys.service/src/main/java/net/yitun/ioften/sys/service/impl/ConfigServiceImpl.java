package net.yitun.ioften.sys.service.impl;

import java.util.Date;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
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
import net.yitun.basic.exp.BizException;
import net.yitun.basic.model.Result;
import net.yitun.basic.model.vo.DeleteVo;
import net.yitun.basic.security.SecurityUtil;
import net.yitun.basic.utils.IdUtil;
import net.yitun.basic.utils.JsonUtil;
import net.yitun.basic.utils.SetUtil;
import net.yitun.ioften.sys.conf.Constant;
import net.yitun.ioften.sys.entity.Config;
import net.yitun.ioften.sys.entity.vo.ConfigQueryVo;
import net.yitun.ioften.sys.model.config.ConfigQuery;
import net.yitun.ioften.sys.model.config.ConfigStore;
import net.yitun.ioften.sys.repository.ConfigRepository;
import net.yitun.ioften.sys.service.ConfigService;

@Slf4j
@Service("sys.ConfigService")
@CacheConfig(cacheNames = Constant.CK_CONF) // 指定Config缓存全局命名空间
public class ConfigServiceImpl implements ConfigService {

    protected Cache cache;

    @Autowired
    protected ConfigRepository repository;

    @Autowired
    protected CacheManager cacheManager;

    @PostConstruct
    protected void init() {
        if (log.isInfoEnabled())
            log.info("{} init ...", this.getClass().getName());

        this.cache = this.cacheManager.getCache(Constant.CK_CONF);
    }

    @Override
    public Config get(Long id) {
        return this.repository.get(id);
    }

    @Override
    @Cacheable(key = "#code")
    public Config getByCode(String code) {
        return this.repository.findByCode(code);
    }

    @Override
    public Page<Config> query(ConfigQuery query) {
        ConfigQueryVo queryVo = new ConfigQueryVo(query.getId(), //
                query.getCode(), query.getName(), query.getScope(), query.getModifier(), query.getModifierId(),
                query.getStime(), query.getEtime());
        return this.repository.query(queryVo);
    }

    @Override
    @Transactional
    @Caching(evict = { @CacheEvict(key = "#model.code") })
    public Result<?> store(ConfigStore model) {
        String code = model.getCode();
        Config config // 配置是否已存在
                = IocUtil.getBean(ConfigService.class).getByCode(code);

        Long uid = SecurityUtil.getId();
        String modifier = SecurityUtil.getName();
        Date nowTime = new Date(System.currentTimeMillis());
        if (null != config) {
            DeleteVo delVo // 逻辑删除配置 -- 起备份作用
                    = new DeleteVo(SetUtil.asSet(config.getId()), uid, modifier, nowTime);
            if (0 == this.repository.delete(delVo))
                return new Result<>(Code.BIZ_ERROR, "配置保存失败, 无法备份");
        }

        Config newConfig = new Config(IdUtil.id(), code //
                , null != config ? config.getName() : model.getName() //
                , null != config ? config.getScope() : model.getScope(), model.getValue() //
                , StringUtils.isBlank(model.getRemark()) ? null != config ? config.getRemark() : model.getRemark()
                        : model.getRemark(),
                modifier, uid, nowTime, nowTime);
        if (!this.repository.create(newConfig))
            throw new BizException(Code.BIZ_ERROR, "配置保存失败, 请检查是否重复");

        if (log.isInfoEnabled())
            log.info("<<< {}.store() id:{}, json:{}", this.getClass().getName(), newConfig.getId(), JsonUtil.toJson(model));

        return Result.OK;
    }

    @Override
    @Transactional
    @Caching(evict = { @CacheEvict(allEntries = true, beforeInvocation = false) })
    public Result<Integer> delete(Set<Long> ids) {
        Long uid = SecurityUtil.getId();
        String modifier = SecurityUtil.getName();

        DeleteVo delVo //
                = new DeleteVo(ids, uid, modifier, new Date(System.currentTimeMillis()));
        int count = this.repository.delete(delVo);
        if (log.isInfoEnabled())
            log.info("<<< {}.delete() ids:{}, count:{}", this.getClass().getName(), JsonUtil.toJson(ids), count);

        return new Result<>(count);
    }

}
