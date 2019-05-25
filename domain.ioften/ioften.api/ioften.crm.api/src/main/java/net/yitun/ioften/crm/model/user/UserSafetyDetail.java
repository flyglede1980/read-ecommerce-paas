package net.yitun.ioften.crm.model.user;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.yitun.ioften.crm.dicts.ThirdSwitch;

/**
 * <pre>
 * <b>用户详细.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月10日 下午2:34:56 LH
 *         new file.
 * </pre>
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = { "id" })
public class UserSafetyDetail implements Serializable {

    @ApiModelProperty(value = "ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
    protected Long id;

    @ApiModelProperty(value = "手机, 长度为11位数字")
    protected String phone;

    @ApiModelProperty(value = "是否设置登录密码, true:已设置; false:未设置")
    protected Boolean hasPasswd;

    @ApiModelProperty(value = "是否设置支付密码, true:已设置; false:未设置")
    protected Boolean hasPayPasswd;

    @ApiModelProperty(value = "是否允许QQ登录, UB:未绑定; ON:已打开; OFF:已关闭")
    protected ThirdSwitch qqChkinSwitch;

    @ApiModelProperty(value = "是否允许微信登录, UB:未绑定; ON:已打开; OFF:已关闭")
    protected ThirdSwitch wxinChkinSwitch;

    @ApiModelProperty(value = "是否允许微博登录, UB:未绑定; ON:已打开; OFF:已关闭")
    protected ThirdSwitch weiboChkinSwitch;

    @ApiModelProperty(value = "是否允许亿豚登录, UB:未绑定; ON:已打开; OFF:已关闭")
    protected ThirdSwitch yitunChkinSwitch;

    /* SVUID */
    private static final long serialVersionUID = 1L;

}
