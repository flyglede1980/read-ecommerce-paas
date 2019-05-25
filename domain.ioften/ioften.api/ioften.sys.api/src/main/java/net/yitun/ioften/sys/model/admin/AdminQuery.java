package net.yitun.ioften.sys.model.admin;

import java.util.Date;
import java.util.Set;

import javax.validation.constraints.Pattern;

import org.apache.commons.lang3.StringUtils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.yitun.basic.Util;
import net.yitun.basic.dict.Status;
import net.yitun.basic.model.Page;

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
 *   0.1   2018年10月17日 下午4:36:45 LH
 *         new file.
 * </pre>
 */
@Setter
@Getter
@ToString
public class AdminQuery extends Page {

    @ApiModelProperty(value = "ID")
    protected Long id;

    @ApiModelProperty(value = "姓名")
    protected String name;

    @ApiModelProperty(value = "账号")
    protected String account;

    @ApiModelProperty(value = "手机号")
    @Pattern(regexp = Util.REGEX_PHONE, message = "手机号无效")
    protected String phone;

    @ApiModelProperty(value = "邮箱")
    protected String email;

    @ApiModelProperty(value = "开始日期")
    protected Date stime;

    @ApiModelProperty(value = "结束日期")
    protected Date etime;

    @ApiModelProperty(value = "角色")
    protected Set<String> role;

    @ApiModelProperty(value = "状态, DISABLE:禁用; ENABLE:正常")
    protected Set<Status> status;

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public AdminQuery() {
        // 排序规则, 默认: ID DESC
        super(null, null, null, "t1.id desc");
    }

    public void setName(String name) {
        this.name = StringUtils.trimToNull(name);
    }

    public void setAccount(String account) {
        this.account = StringUtils.trimToNull(account);
    }

    public void setPhone(String phone) {
        this.phone = StringUtils.trimToNull(phone);
    }

    public void setEmail(String email) {
        this.email = StringUtils.trimToNull(email);
    }

}
