package net.yitun.comon.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * <pre>
 * <b>Obs存储配置.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月20日 上午10:42:46 LH
 *         new file.
 * </pre>
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "basic.store.obs")
public class ObsConfig {

    protected String bucket = "priv"; // 私密桶

    protected String endpoint; // 端点

    protected String accessKey;

    protected String accessSecret;

    protected String pubBucket = "pub"; // 公共桶

    protected String pubEndpoint;

    protected String pubAccessKey;

    protected String pubAccessSecret;

    protected String schema = "https";

}
