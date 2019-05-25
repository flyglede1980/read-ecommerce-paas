package net.yitun.ioften.crm.entity.vo;

import java.util.Collection;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.yitun.ioften.crm.dicts.IdentityStatus;
import net.yitun.ioften.crm.dicts.Type;
import net.yitun.ioften.crm.entity.Identity;

/**
 * <pre>
 * <b>用户 认证查询Vo.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月6日 下午7:33:25 LH
 *         new file.
 * </pre>
 */
@Setter
@Getter
@ToString
public class IdentityQueryVo extends Identity {

    @ApiModelProperty(value = "开始日期")
    protected Date stime;

    @ApiModelProperty(value = "结束日期")
    protected Date etime;

    @ApiModelProperty(value = "类型, N:未知; PE:个人, EN:企业, EW:长见号")
    protected Collection<Type> types;

    @ApiModelProperty(value = "审核状态, NEW:新申请, REVIEW:认证中; REFUSE:拒绝; APPROVE:已认证")
    protected Collection<IdentityStatus> statuss;

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public IdentityQueryVo(Long id, Long uid, String name, String phone, Collection<Type> types,
            Collection<IdentityStatus> statuss) {
        super(id, uid, null /* type */, name, phone, null, null, null, null, null, null, null, null, null, null, null, null,
                null, null, null);
        this.types = types;
        this.statuss = statuss;
    }

}
