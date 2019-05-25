package net.yitun.ioften.crm.model.user;

import java.util.Date;
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
import net.yitun.basic.dict.Sex;
import net.yitun.basic.dict.Status;
import net.yitun.basic.model.Page;
import net.yitun.ioften.crm.dicts.Level;
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
 *   0.1   2018年10月17日 下午4:36:45 LH
 *         new file.
 * </pre>
 */
@Setter
@Getter
@ToString
public class UserQuery extends Page {

    @Min(value = Util.MIN_ID, message = "ID无效")
    @ApiModelProperty(value = "ID")
    protected Long id;

    @Size(max = 16, message = "昵称长度为2~16个字符")
    @ApiModelProperty(value = "昵称, 长度为2~16个字符")
    protected String nick;

    @Size(max = 32, message = "实名长度为2~32个字符")
    @ApiModelProperty(value = "实名, 长度为2~32个字符")
    protected String name;

    @ApiModelProperty(value = "手机, 长度为11位数字")
    @Pattern(regexp = Util.REGEX_PHONE, message = "手机号无效")
    protected String phone;

    @Size(max = 16, message = "城市长度为2~16个字符")
    @ApiModelProperty(value = "城市, 长度为2~16个字符")
    protected String city;

    @Size(max = 18, message = "证件号度为8~18个字符")
    @ApiModelProperty(value = "证件号, 长度为8~18个字符")
    protected String idcard;

    @Size(max = 16, message = "运营者, 仅企业, 长见号, 长度为2~16个字符")
    @ApiModelProperty(value = "运营者, 仅企业, 长见号, 长度为2~16个字符")
    protected String operator;

    @Size(max = 6, message = "邀请码长度为6个字符")
    @ApiModelProperty(value = "邀请码, 长度为6个字符")
    protected String invite;

    @Size(max = 32, message = "终端ID长度为0~32个字符")
    @ApiModelProperty(value = "终端ID, 长度为0~32个字符")
    protected String device;

    @Min(value = Util.MIN_ID, message = "类目ID无效")
    @ApiModelProperty(value = "类目ID")
    protected Long categoryId;

    @Min(value = Util.MIN_ID, message = "子类目ID无效")
    @ApiModelProperty(value = "子类目ID")
    protected Long subCategoryId;

    @ApiModelProperty(value = "开始日期")
    protected Date stime;

    @ApiModelProperty(value = "结束日期")
    protected Date etime;

    @ApiModelProperty(value = "性别, N:未知; M:男士; F:女士")
    protected Set<Sex> sex;

    @ApiModelProperty(value = "类型, N:无, PE:个人, EN:企业, EW:长见号")
    protected Set<Type> type;

    @ApiModelProperty(value = "等级, N, VIP1, VIP2, ....")
    protected Set<Level> level;

    @ApiModelProperty(value = "状态, DISABLE:禁用; ENABLE:正常")
    protected Set<Status> status;

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public UserQuery() {
        // 排序规则, 默认: ID DESC
        super(null, null, null, "t1.id desc");
    }

    public void setNick(String nick) {
        this.nick = StringUtils.trimToNull(nick);
    }

    public void setName(String name) {
        this.name = StringUtils.trimToNull(name);
    }

    public void setCity(String city) {
        this.city = StringUtils.trimToNull(city);
    }

    public void setIdcard(String idcard) {
        this.idcard = StringUtils.trimToNull(idcard);
    }

    public void setOperator(String operator) {
        this.operator = StringUtils.trimToNull(operator);
    }

}
