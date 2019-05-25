package net.yitun.ioften.crm.model.others.oauth;

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

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = { "id" })
public class OauthDetail implements Serializable {

    @ApiModelProperty(value = "ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
    protected Long id;

    @ApiModelProperty(value = "用户ID, 外键: crm_user.id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
    protected Long uid;

    @ApiModelProperty(value = "方式, 微信, 微博, 叮叮, 支付宝")
    protected Integer way;

    @ApiModelProperty(value = "授权信息")
    protected String oauth;

    @ApiModelProperty(value = "三方授权ID")
    protected String openid;

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public OauthDetail(Long id) {
        super();
        this.id = id;
    }

}
