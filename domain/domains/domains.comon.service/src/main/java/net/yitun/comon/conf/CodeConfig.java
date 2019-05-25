package net.yitun.comon.conf;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * <pre>
 * <b>验证码配置.</b>
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
@ConfigurationProperties(prefix = "basic.code")
public class CodeConfig {

    protected int imgLen = 6; // 字符长度, 默认: 6

    protected long imgTTL = 120; // 缓存有效期, 默认: 120s

    protected long imgRate = 2; // 刷新频率, 默认: 2秒/次

    // =======================================
    protected int smsLen = 4; // 字符长度, 默认: 4

    protected long smsTTL = 300; // 缓存有效期, 默认: 300s

    protected long smsRate = 30; // 发送频率, 默认: 30秒/条

    protected long smsLockTTL = 3600; // 黑名单锁定时长, 默认: 3600s

    protected long smsFireLockTimes = 3; // 累计触发锁定次数, 默认: 3

    protected long smsIgnoreVerifyTimes = 1; // 忽略图形验证次数, 默认: 1

    protected String smsTmpl = "【XXX】您的验证码为：%s，切勿泄露于他人。"; // 短信模板

    // 时间计算单位
    protected TimeUnit unit = TimeUnit.SECONDS;

}
