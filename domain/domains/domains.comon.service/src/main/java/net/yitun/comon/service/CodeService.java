package net.yitun.comon.service;

import javax.servlet.http.HttpServletResponse;

import net.yitun.basic.model.Result;
import net.yitun.comon.dicts.Type;

/**
 * <pre>
 * <b>验证码服务.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月8日 下午8:15:52 LH
 *         new file.
 * </pre>
 */
public interface CodeService {

    /**
     * 输出图片验证码</br>
     * 6个数字字符, 有效期120秒，刷新频率最快3秒/次
     * 
     * @param cache 是否缓存
     * @return mark 持有验证码标记
     * @param response HTTP响应
     * @return Result<?> 输出结果
     */
    Result<?> outImg(boolean cache, String mark, HttpServletResponse response);

    /**
     * 发送短信验证码</br>
     * 先进行图形码验证, 然后发送短信: 4个数字字符, 有效期300秒，发送频率最快30秒/条
     * 
     * @param cache 是否缓存
     * @return phone 手机号
     * @param code 图形验证码
     * @return Result<String> 发送结果
     */
    Result<String> sendSms(boolean cache, String phone, String code);

    /**
     * 验证码匹配
     * 
     * @return type 类型
     * @return key 缓存KEY
     * @param code 待验证的验证码
     * @return Result<?> 是否匹配
     */
    Result<?> match(Type type, String key, String code);

}
