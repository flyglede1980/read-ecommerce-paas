package net.yitun.comon.conf;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * <b>通用模块.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月20日 上午10:41:39 LH
 *         new file.
 * </pre>
 */
@Slf4j
@Configuration
public class ComonConfig {

    @PostConstruct
    protected void init() {
        if (log.isInfoEnabled())
            log.info("{} init ...", this.getClass().getName());
    }

}
