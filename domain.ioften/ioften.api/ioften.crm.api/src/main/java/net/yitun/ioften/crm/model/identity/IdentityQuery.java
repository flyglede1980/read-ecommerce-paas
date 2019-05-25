package net.yitun.ioften.crm.model.identity;

import java.util.Set;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.yitun.basic.Util;
import net.yitun.basic.model.Page;
import net.yitun.ioften.crm.dicts.IdentityStatus;
import net.yitun.ioften.crm.dicts.Type;

/**
 * <pre>
 * <b>分页查询.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月13日 下午3:41:06 LH
 *         new file.
 * </pre>
 */
@Setter
@Getter
@ToString
public class IdentityQuery extends Page {

    @Min(value = Util.MIN_ID, message = "ID无效")
    @ApiModelProperty(value = "ID")
    protected Long id;

    @Min(value = Util.MIN_ID, message = "用户ID无效")
    @ApiModelProperty(value = "用户ID")
    protected Long uid;

    @Size(max = 32, message = "实名长度为2~32个字符")
    @ApiModelProperty(value = "实名, 长度为2~32个字符")
    protected String name;

    @ApiModelProperty(value = "手机, 长度为11位数字")
    @Pattern(regexp = Util.REGEX_PHONE, message = "手机号无效")
    protected String phone;

    @ApiModelProperty(value = "类型, N:未知; PE:个人, EN:企业, EW:长见号")
    protected Set<Type> type;

    @ApiModelProperty(value = "审核状态, NEW:新申请, REVIEW:认证中; REFUSE:拒绝; APPROVE:已认证")
    protected Set<IdentityStatus> status;

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public IdentityQuery() {
        // 排序规则, 默认: ID DESC
        super(null, null, null, "t1.id desc");
    }

    public void setName(String name) {
        this.name = StringUtils.trimToNull(name);
    }

}
