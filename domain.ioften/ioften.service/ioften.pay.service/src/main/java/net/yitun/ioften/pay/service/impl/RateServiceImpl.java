package net.yitun.ioften.pay.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;
import net.yitun.basic.model.Result;
import net.yitun.basic.security.SecurityUtil;
import net.yitun.basic.utils.CalcUtil;
import net.yitun.ioften.pay.conf.Constant;
import net.yitun.ioften.pay.service.RateService;
import net.yitun.ioften.pay.support.BlockSupport;
import net.yitun.ioften.sys.ConfigApi;
import net.yitun.ioften.sys.dicts.Scope;
import net.yitun.ioften.sys.model.config.ConfigDetail;
import net.yitun.ioften.sys.model.config.ConfigStore;

@Slf4j
@Service("cms.RateService")
@CacheConfig(cacheNames = Constant.CKEY_RATE)
public class RateServiceImpl implements RateService {

    @Autowired
    protected ConfigApi configApi;

    // RESTful Api 调用模板
    protected RestTemplate restTmpl;

    @PostConstruct
    protected void init() {
        if (log.isInfoEnabled())
            log.info("{} init ...", this.getClass().getName());

        // 初始化 Api 调用模板
        this.restTmpl = new RestTemplate(new SimpleClientHttpRequestFactory() {
            {
                this.setReadTimeout(1000); // 等待响应超时
                this.setConnectTimeout(600); // Socket连接超时
            }
        });
    }

    @Override
    @Cacheable(key = "'now'") // 缓存映射, 降低对API调用频率
    public Long now() {
        // 1、方案1: 同时分别获取 USD/CNY USD/SDC 的汇率，再进行记录
        // 2、方案2：如果以上 USD/CNY USD/SDC 汇率任何一个获取失败，则直接尝试获取 CNY/SDC 汇率（此汇率进度不高）
        // 3、如果以上汇率获取任一方案失败，则通过系统缓存获取（缓存未建立时，通过DB即系统配置获取）
        // 4、以上非缓存获取的汇率都需要更新缓存和DB

        Long newRate = null;
        Object usd_cny = BlockSupport.invoke(Constant.API_USD_CNY_RATE);
        Object usd_sdc = BlockSupport.invoke(Constant.API_USD_SDC_RATE);
        // USD/CNY USD/SDC 的汇率 全都获取成功
        if (null != usd_cny && null != usd_sdc) {
            newRate = new BigDecimal(String.valueOf(usd_cny)) // usd_cny * usd_sdc * 100000000
                    .multiply(new BigDecimal(String.valueOf(usd_sdc))) //
                    .multiply(new BigDecimal(CalcUtil.SCALE)).setScale(0, RoundingMode.HALF_UP).longValue();
        }

        // USD/CNY USD/SDC 任意汇率获取失败，需要直接获取 CNY/SDC 汇率
        if (null == newRate) {
            Object cny_sdc = BlockSupport.invoke(Constant.API_CNY_SDC_RATE);
            if (null != cny_sdc) {
                newRate = new BigDecimal(String.valueOf(cny_sdc)) // cny_sdc * 100000000
                        .multiply(new BigDecimal(CalcUtil.SCALE)).setScale(0, RoundingMode.HALF_UP).longValue();
            }
        }

        // 获取缓存汇率，内部判断缓存没有则直接从DB加载
        ConfigDetail config = this.configApi.code(Constant.RATE_CONF);
        if (null == newRate && null != config && null != config.getValue())
            newRate = Long.valueOf(config.getValue()); // 以上三个API均无发有效获取，则提取缓存汇率

        // 如果新汇率与当前存档不一致时需要同步更新本地存档
        else if (null != newRate && (null == config || !newRate.equals(Long.valueOf(config.getValue())))) {
            SecurityUtil.auto(0L); // 开启系统操作权限
            ConfigStore newConfig //
                    = new ConfigStore(Constant.RATE_CONF, "CNY/SDC 汇率", Scope.ALL, newRate, null);
            try {
                this.configApi.store(newConfig);
            } finally {
                SecurityUtil.reset();
            }
        }

        return newRate;
    }

    @Override
    @CacheEvict(key = "'now'", beforeInvocation = true)
    public Result<Boolean> refresh() {
        return new Result<>(true);
    }

}
