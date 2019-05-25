package net.yitun.ioften.crm.entity.others;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = { "id" })
public class Oauth implements Serializable {

    @ApiModelProperty(value = "ID")
    protected Long id;

    @ApiModelProperty(value = "用户ID, 外键: crm_user.id")
    protected Long uid;

    @ApiModelProperty(value = "方式, 微信, 微博, 叮叮, 支付宝")
    protected Integer way;

    @ApiModelProperty(value = "授权信息")
    protected String oauth;

    @ApiModelProperty(value = "三方授权ID")
    protected String openid;

    @ApiModelProperty(value = "创建时间")
    protected Date ctime;

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public Oauth(Long id) {
        super();
        this.id = id;
    }

}
