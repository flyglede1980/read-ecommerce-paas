package net.yitun.comon.service.impl;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import net.yitun.basic.Code;
import net.yitun.basic.cache.support.AtomicOperations;
import net.yitun.basic.model.Result;
import net.yitun.basic.utils.CodeUtil;
import net.yitun.comon.conf.CodeConfig;
import net.yitun.comon.dicts.Type;
import net.yitun.comon.service.CodeService;
import net.yitun.comon.support.CaptchaSupport;
import net.yitun.comon.support.sms.SmsSupport;

@Service("comon.CodeService")
@SuppressWarnings("unchecked")
public class CodeServiceImpl implements CodeService {

    @Autowired // 验证码配置
    protected CodeConfig conf;

    @Autowired // 短信网关支撑
    protected SmsSupport sms;

    @Autowired // 图形验证码支撑
    protected CaptchaSupport captcha;

    @Autowired // 用于缓存用户申请的验证码，以及记录用户申请的时间、次数等信息
    protected ValueOperations<String, String> cache;
    @Autowired // 生成验证码次数缓存器
    protected AtomicOperations<String, Long> cacheTimes;

    // 图片验证码缓存KEY
    protected static final String IMG_CKEY = "_code:img:";
    // 短信验证码缓存KEY
    protected static final String SMS_CKEY = "_code:sms:";

    /**
     * 构建 ckey
     * 
     * @param type
     * @param key
     * @return String
     */
    private String ckey(Type type, String key) {
        if (Type.IMG == type)
            return IMG_CKEY + key;

        return SMS_CKEY + key;
    }

    @Override
    public Result<?> outImg(boolean cache, String mark, HttpServletResponse response) {
        String cKey = this.ckey(Type.IMG, mark);

        // 有缓存，则判断是否高频刷新, 频率必须大于 imgRate 秒/次, 减轻服务器IO压力
        if (cache && this.unRefresh(cKey, this.conf.getImgTTL(), this.conf.getImgRate()))
            return new Result<>(Code.BIZ_ERROR, "勿频繁刷新验证码");

        // 生产 imgLen 位数的验证码
        String code = CodeUtil.code(this.conf.getImgLen());
        if (cache) // 需要缓存，则置入缓存中并设置有效期为 imgTTL
            this.cache.set(cKey, code, this.conf.getImgTTL(), this.conf.getUnit());

        // 向用户终端输出图形验证码图片
        boolean isOutput = this.captcha.outImage(code, response);

        return isOutput ? null : new Result<>(Code.BIZ_ERROR, "图片验证码下载失败");
    }

    @Override
    public Result<String> sendSms(boolean cache, String phone, String code) {
        String cKey = this.ckey(Type.SMS, phone);
        // 有缓存，则判断是否高频刷新, 频率必须大于 smsRate 秒/次, 短信流量浪费
        if (cache && this.unRefresh(cKey, this.conf.getSmsTTL(), this.conf.getSmsRate()))
            return new Result<>(Code.BIZ_ERROR, "勿频繁获取短信验证码");

        Long times = null;
        // 是否开启了短信次数限制功能
        String timesKey = this.ckey(Type.SMS, "times:" + phone);
        if (1 <= this.conf.getSmsFireLockTimes() || 1 <= this.conf.getSmsIgnoreVerifyTimes())
            times = this.cacheTimes.getIncr(timesKey); // 获取同一手机号累计发送SMS次数

        // 有缓存并短信验证码超过 N 次，则先判断图片验证码是否匹配
        if (null != times && this.conf.getSmsIgnoreVerifyTimes() <= times //
                && cache && !this.match(Type.IMG, phone, code).ok())
            return new Result<>(Code.BIZ_ERROR, "图形验证码错误", "NEED_IMG_CODE");

        // 在smsLockTTL 内, 前 N 次可不验证图形验证码；超过 X 次后直接拒绝图形验证和发送短信
        if (null != times && this.conf.getSmsFireLockTimes() <= times)
            return new Result<>(Code.BIZ_ERROR, "短信验证码发送太频繁, 请2小时后再试", "SMS_LOCKED:" + this.conf.getSmsLockTTL());

        // 生产 smsLen 位数的验证码并套用短信模板
        String _code = CodeUtil.code(this.conf.getSmsLen());
        String content = String.format(this.conf.getSmsTmpl(), _code);
        if (cache) // 需要缓存，则置入缓存中并设置有效期为 smsTTL
            this.cache.set(cKey, _code, this.conf.getSmsTTL(), this.conf.getUnit()); // 设置短信有效时间

        // 是否开启了短信次数限制功能
        if (1 <= this.conf.getSmsFireLockTimes() || 1 <= this.conf.getSmsIgnoreVerifyTimes())
            times = this.cacheTimes.incr(timesKey, 1, this.conf.getSmsLockTTL(), this.conf.getUnit()); // 累计发送一次SMS

        // 调用短信支持，就行发现送短信
        String error = this.sms.send(phone, content);

        return null == error ? Result.OK : new Result<>(Code.BIZ_ERROR, "短信验证码发送失败");
    }

    @Override
    public Result<?> match(Type type, String key, String code) {
        if (StringUtils.isBlank(code) || 4 > code.length() || 6 < code.length())
            return new Result<>(Code.BAD_REQ, "验证码无效"); // 验证码不匹配

        // 不满足需删除缓存
        String cKey = this.ckey(type, key);
        String original = this.cache.get(cKey);
        if (null != original) // 有验证码, 此时需要立即移除，便于用户输入与此不匹配时能马上获取最新的验证码
            this.cache.getOperations().delete(cKey);

        // 匹配验证码, 支持超级验证码校验: 1010 101010
        if (!(StringUtils.equalsAny(code, "1010", "101010") || StringUtils.equals(code, original)))
            return new Result<>(Code.BIZ_ERROR, "验证码不匹配"); // 验证码不匹配

        String timesKey = this.ckey(type, "times:" + key);
        // 是否开启了短信次数限制功能，验证码相同时删除计数器
        if (Type.SMS == type && (1 <= this.conf.getSmsFireLockTimes() || 1 <= this.conf.getSmsIgnoreVerifyTimes()))
            this.cacheTimes.delete(timesKey);

        return Result.OK; // 验证码匹配
    }

    /**
     * 是否不可(存在频繁)刷新验证码
     * 
     * @param ckey
     * @param ttl  有效期
     * @param rate 频率
     * @return boolean 是否不可刷新
     */
    private boolean unRefresh(String ckey, long ttl, long rate) {
        Long expire = null;
        return !(null == (expire = this.cache.getOperations().getExpire(ckey)) //
                || 0L >= expire || (ttl - expire) >= rate);
    }

}
