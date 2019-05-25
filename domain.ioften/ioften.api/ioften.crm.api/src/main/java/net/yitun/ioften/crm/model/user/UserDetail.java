package net.yitun.ioften.crm.model.user;

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
import net.yitun.basic.dict.Sex;
import net.yitun.basic.dict.Status;
import net.yitun.ioften.crm.dicts.Level;
import net.yitun.ioften.crm.dicts.Type;

/**
 * <pre>
 * <b>用户详细.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月10日 下午2:34:56 LH
 *         new file.
 * </pre>
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = { "id" })
public class UserDetail implements Serializable {

    @ApiModelProperty(value = "ID, 获取用户头像的格式: https://cj-pub.obs.myhwclouds.com/header/{id}")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
    protected Long id;

    @ApiModelProperty(value = "昵称, 长度为2~16个字符")
    protected String nick;

    @ApiModelProperty(value = "实名, 长度为2~32个字符")
    protected String name;

    @ApiModelProperty(value = "手机, 长度为11位数字")
    protected String phone;

    @ApiModelProperty(value = "性别, N:未知; M:男士; F:女士")
    protected Sex sex;

    @ApiModelProperty(value = "类型, N:无, PE:个人, EN:企业, EW:长见号")
    protected Type type;

    @ApiModelProperty(value = "等级, N, VIP1, VIP2, ....")
    protected Level level;

    @ApiModelProperty(value = "城市, 长度为2~16个字符")
    protected String city;

    @ApiModelProperty(value = "生日")
    @JSONField(format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
    protected Date birthday;

    @ApiModelProperty(value = "证件号, 长度为8~18个字符")
    protected String idcard;

    @ApiModelProperty(value = "运营者, 仅企业, 长见号, 长度为2~16个字符")
    protected String operator;

    @ApiModelProperty(value = "简介, 长度为0~255个字符")
    protected String intro;

    @ApiModelProperty(value = "邀请码, 长度为6个字符")
    protected String invite;

    @ApiModelProperty(value = "终端ID, 长度为0~32个字符")
    protected String device;

    @ApiModelProperty(value = "类目ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
    protected Long categoryId;

    @ApiModelProperty(value = "类目名称")
    protected String categoryName;

    @ApiModelProperty(value = "子类目ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
    protected Long subCategoryId;

    @ApiModelProperty(value = "子类目名称")
    protected String subCategoryName;

    @ApiModelProperty(value = "备注, 长度为0~128个字符")
    protected String remark;

    @ApiModelProperty(value = "状态, DISABLE:禁用; ENABLE:正常")
    protected Status status;

    @ApiModelProperty(value = "创建时间")
    protected Date ctime;

    @ApiModelProperty(value = "修改时间")
    protected Date mtime;

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public UserDetail(Long id) {
        super();
        this.id = id;
    }

    public UserDetail(Long id, String nick, Level level, String invite) {
        this(id, nick, level, null, invite);
    }

    public UserDetail(Long id, String nick, Level level, String intro, String invite) {
        super();
        this.id = id;
        this.nick = nick;
        this.level = level;
        this.intro = intro;
        this.invite = invite;
    }

    @JSONField(format = "yyyy-MM-dd")
    public Date getBirthday() {
        return birthday;
    }

}
