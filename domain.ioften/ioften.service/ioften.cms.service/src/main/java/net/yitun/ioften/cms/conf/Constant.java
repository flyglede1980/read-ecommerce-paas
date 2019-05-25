package net.yitun.ioften.cms.conf;

import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;

import lombok.extern.slf4j.Slf4j;
import net.yitun.basic.cache.support.locker.RedisLock;
import net.yitun.basic.utils.SetUtil;

/**
 * <pre>
 * <b>CMS 常量.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年8月22日 下午3:02:19 LH
 *         new file.
 * </pre>
 */
@Slf4j
@Configuration
@Order(Integer.MAX_VALUE)
public class Constant {

    /** 资讯板块配置 */
    public static final String CMS_CONF = "cms.conf";
    // =================================================================

    /** 我的设置缓存 */
    public static final String CK_MY_CONF = "cms.my";

    /** 我关注的话题缓存 */
    public static final String CK_MY_TOPIC = "cms.my.topic#3600#-1"; // 缓存1H

    /** 挖矿任务缓存 */
    public static final String CK_GAIN = "cms.gain#120#-1"; // 缓存120s

    /** 资讯分享记录缓存 */
    public static final String CK_SHARE = "cms.share#86400#-1"; // 缓存24H

    /** 资讯浏览记录缓存 */
    public static final String CK_BROWSE = "cms.browse#3600#-1"; // 缓存1H

    /** 资讯收藏记录缓存 */
    public static final String CK_FAVORITE = "cms.favorite#3600#-1"; // 缓存1H

    /** 资讯栏目缓存 */
    public static final String CKEY_CATE = "cms.cate#3600#-1"; // 缓存1H

    /** 资讯内容缓存 */
    public static final String CKEY_ARTICLE = "cms.article#3600#-1"; // 缓存1H
    // =================================================================

    /** 官方长鉴号账号清单 */
    public static final Set<Long> OFFICIAL_ACCOUNT = SetUtil.asLinkedSet(1041794429293166592L);

    @PostConstruct
    protected void init() {
        if (log.isInfoEnabled())
            log.info("{} init ...", this.getClass().getName());
    }

    @Bean
    public RedisLock redisDistributedLock(RedisTemplate<String, Object> redisTemplate) {
        return new RedisLock(redisTemplate);
    }

}
