package net.yitun.ioften.cms.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.yitun.basic.dict.Status;
import net.yitun.basic.dict.YesNo;

/**
 * <pre>
 * <b>资讯 类目模型.</b>
 * <b>Description:</b>
 *
 * <b>Author:</b> XJN
 * <b>Date:</b> 2018-11-12 11:17:00.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月12日 上午11:17:06 XJN
 *         new file.
 * </pre>
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = { "classId" })
public class Category implements Serializable {
    @ApiModelProperty(value = "栏目分类编号")
    protected Long classId;
    @ApiModelProperty(value = "创建者账号")
    protected Long usersId;
    @ApiModelProperty(value = "上级分类编号")
    protected Long parentId;
    @ApiModelProperty(value = "分类代码，长度不超过30个字符")
    protected String code;
    @ApiModelProperty(value = "分类名称，长度不超过50个字符")
    protected String name;
    @ApiModelProperty(value = "级数, 默认值为1")
    protected Integer level;
    @ApiModelProperty(value = "层级关系，长度不超过300个字符，自动生成")
    protected String relation;
    @ApiModelProperty(value = "分类图标，存储图片的路径")
    protected String icon;
    @ApiModelProperty(value = "分类描述")
    protected String description;
    @ApiModelProperty(value = "排序编号，默认为0")
    protected Integer sortId;
    @ApiModelProperty(value = "是否启用")
    protected Status isEnabled;
    @ApiModelProperty(value = "是否热门")
    protected YesNo isHot;
    @ApiModelProperty(value = "是否推荐")
    protected YesNo isRecommend;
    @ApiModelProperty(value = "是否置顶")
    protected YesNo isTop;
    @ApiModelProperty(value = "是否需要授权")
    protected YesNo isAuthorized;
    @ApiModelProperty(value = "创建时间")
    protected Date cDate;
    @ApiModelProperty(value = "修改时间")
    protected Date mDate;
    @ApiModelProperty(value = "父类目")
    protected Category parent;
    @ApiModelProperty(value = "子类目清单")
    protected List<Category> childs;
    /* SVUID */
    private static final long serialVersionUID = 1L;

    public Category(Long classId) {
        super();
        this.classId = classId;
    }

    public Category(Integer level, Long parentId, Date mDate) {
        this.level = level;
        this.parentId = parentId;
        this.mDate = mDate;
    }

    public Category(Long classId, Long usersId, Long parentId, String code, String name, Integer level, String relation, String icon,
                    String description, Integer sortId, Status isEnabled, YesNo isHot, YesNo isRecommend, YesNo isTop, YesNo isAuthorized,
                    Date cDate, Date mDate) {
        super();
        this.classId = classId;
        this.usersId = usersId;
        this.parentId = parentId;
        this.code = code;
        this.name = name;
        this.level = level;
        this.relation = relation;
        this.icon = icon;
        this.description = description;
        this.sortId = sortId;
        this.isEnabled = isEnabled;
        this.isHot = isHot;
        this.isRecommend = isRecommend;
        this.isTop = isTop;
        this.isAuthorized = isAuthorized;
        this.cDate = cDate;
        this.mDate = mDate;
    }
}