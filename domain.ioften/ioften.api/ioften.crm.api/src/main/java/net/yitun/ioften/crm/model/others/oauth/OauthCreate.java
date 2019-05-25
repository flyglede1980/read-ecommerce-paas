package net.yitun.ioften.crm.model.others.oauth;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OauthCreate implements Serializable {

    @ApiModelProperty(value = "用户ID, 外键: crm_user.id")
    @NotNull(message = "用户ID无效")
    protected Long uid;

    @ApiModelProperty(value = "方式, 微信, 微博, 叮叮, 支付宝")
    protected Integer way;

    @ApiModelProperty(value = "授权信息")
    protected String oauth;

    @ApiModelProperty(value = "三方授权ID")
    protected String openid;

    /* SVUID */
    private static final long serialVersionUID = 1L;

}
