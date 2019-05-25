package net.yitun.ioften.crm.entity;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.yitun.ioften.crm.dicts.IdentityStatus;
import net.yitun.ioften.crm.dicts.Type;

/**
 * <pre>
 * <b>用户 认证模型.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月13日 下午2:59:48 LH
 *         new file.
 * </pre>
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = { "id" })
public class Identity implements Serializable {

    @ApiModelProperty(value = "ID")
    protected Long id;

    @ApiModelProperty(value = "用户ID, 外键: crm_user.id")
    protected Long uid;

    @ApiModelProperty(value = "类型, N:未知; PE:个人, EN:企业, EW:长见号")
    protected Type type;

    @ApiModelProperty(value = "实名, 长度为2~32个字符")
    protected String name;

    @ApiModelProperty(value = "手机, 长度为11位数字")
    protected String phone;

    @ApiModelProperty(value = "城市, 长度为2~16个字符")
    protected String city;

    @ApiModelProperty(value = "证件号, 长度为8~18个字符")
    protected String idcard;

    @ApiModelProperty(value = "证明样照, 多个逗号分隔")
    protected String evidences;

    @ApiModelProperty(value = "运营者, 仅企业, 长见号, 长度为2~16个字符")
    protected String operator;

    @ApiModelProperty(value = "类目ID")
    protected Long categoryId;

    @ApiModelProperty(value = "类目名称")
    protected String categoryName;

    @ApiModelProperty(value = "子类目ID")
    protected Long subCategoryId;

    @ApiModelProperty(value = "子类目名称")
    protected String subCategoryName;

    @ApiModelProperty(value = "审批人")
    protected String audit;

    @ApiModelProperty(value = "审批人ID, 外键: sys_admin.id")
    protected Long auditId;

    @ApiModelProperty(value = "审批时间")
    protected Date auditTime;

    @ApiModelProperty(value = "审批备注, 长度为0~255个字符")
    protected String auditRemark;

    @ApiModelProperty(value = "审核状态, NEW:新申请, REVIEW:认证中; REFUSE:拒绝; APPROVE:已认证")
    protected IdentityStatus status;

    @ApiModelProperty(value = "创建时间")
    protected Date ctime;

    @ApiModelProperty(value = "修改时间")
    protected Date mtime;

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public Identity(Long id) {
        super();
        this.id = id;
    }

    public Identity(Long id, Date mtime) {
        super();
        this.id = id;
        this.mtime = mtime;
    }

    public Identity(Long id, IdentityStatus status, Date mtime) {
        super();
        this.id = id;
        this.status = status;
        this.mtime = mtime;
    }

    public Identity(Long id, String audit, Long auditId, Date auditTime, String auditRemark, IdentityStatus status,
            Date mtime) {
        super();
        this.id = id;
        this.audit = audit;
        this.auditId = auditId;
        this.auditTime = auditTime;
        this.auditRemark = auditRemark;
        this.status = status;
        this.mtime = mtime;
    }
}
