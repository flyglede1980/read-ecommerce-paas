package net.yitun.ioften.crm.model.invite;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.yitun.ioften.crm.dicts.InviteStatus;
import net.yitun.ioften.crm.model.user.UserDetail;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = { "id" })
public class InviteDetail implements Serializable {

    @ApiModelProperty(value = "ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
    protected Long id;

    @ApiModelProperty(value = "被邀请用户ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
    protected Long uid;

    @ApiModelProperty(value = "发起邀请用户ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
    protected Long fuid;

    @ApiModelProperty(value = "被邀请手机号, 长度为11位数字")
    protected String phone;

    @ApiModelProperty(value = "挖矿金额")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
    protected Long award;

    @ApiModelProperty(value = "邀请状态: LOGING:仅登记; SIGNUP:已注册")
    protected InviteStatus status;

    @ApiModelProperty(value = "创建时间")
    protected Date ctime;

    @ApiModelProperty(value = "修改时间")
    protected Date mtime;

    @ApiModelProperty(value = "被邀请用户信息")
    protected UserDetail inviteUser;

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public InviteDetail(Long id) {
        super();
        this.id = id;
    }

}
