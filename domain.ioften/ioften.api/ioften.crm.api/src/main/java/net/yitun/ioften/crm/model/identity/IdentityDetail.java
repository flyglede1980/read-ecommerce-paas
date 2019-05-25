package net.yitun.ioften.crm.model.identity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.yitun.ioften.crm.dicts.IdentityStatus;
import net.yitun.ioften.crm.dicts.Type;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = { "id" })
public class IdentityDetail implements Serializable {

    @ApiModelProperty(value = "ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
    protected Long id;

    @ApiModelProperty(value = "用户ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
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
    protected List<String> evidences;

    @ApiModelProperty(value = "运营者, 仅企业, 长见号, 长度为2~16个字符")
    protected String operator;

    @ApiModelProperty(value = "类目ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
    protected Long categoryId;

    @ApiModelProperty(value = "类目名称, 长度为2~16个字符")
    protected String categoryName;

    @ApiModelProperty(value = "子类目ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
    protected Long subCategoryId;

    @ApiModelProperty(value = "子类目名称, 长度为2~16个字符")
    protected String subCategoryName;

    @ApiModelProperty(value = "审批人")
    protected String audit;

    @ApiModelProperty(value = "审批人ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
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

    public IdentityDetail(Long id) {
        super();
        this.id = id;
    }

    public IdentityDetail(Long id, Long uid, Type type, String name, String phone, String city, String idcard, String evidences,
            String operator, Long categoryId, String categoryName, Long subCategoryId, String subCategoryName, String audit,
            Long auditId, Date auditTime, String auditRemark, IdentityStatus status, Date ctime, Date mtime) {
        super();
        this.id = id;
        this.uid = uid;
        this.type = type;
        this.name = name;
        this.phone = phone;
        this.city = city;
        this.idcard = idcard;
        this.operator = operator;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.subCategoryId = subCategoryId;
        this.subCategoryName = subCategoryName;
        this.audit = audit;
        this.auditId = auditId;
        this.auditTime = auditTime;
        this.auditRemark = auditRemark;
        this.status = status;
        this.ctime = ctime;
        this.mtime = mtime;
        this.setEvidences(evidences);
    }

    public IdentityDetail setEvidences(String evidences) {
        if (null != evidences && 0 != evidences.trim().length())
            this.evidences = Arrays.asList(evidences.split(","));

        return this;
    }

}
