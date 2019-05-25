package net.yitun.ioften.cms.entity.columnclasses;

import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.yitun.basic.dict.Status;
import net.yitun.basic.dict.YesNo;
/**
 * 栏目分类(包括文章分类、广告分类等)信息
 * @since 1.0.0
 * @see ApiModelProperty
 * @author Flyglede
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ColumnClasses{

    @ApiModelProperty(value = "分类编号")
    protected Long classId;

    @ApiModelProperty(value = "创建者")
    protected Long usersId;

    @ApiModelProperty(value = "上级分类")
    protected Long parentId;

    @ApiModelProperty(value = "分类代码")
    protected String code;

    @ApiModelProperty(value = "分类名称")
    protected String name;

    @ApiModelProperty(value = "级数")
    protected Integer level;

    @ApiModelProperty(value = "关系")
    protected String relation;

    @ApiModelProperty(value = "分类图标")
    protected String icon;

    @ApiModelProperty(value = "分类描述")
    protected String description;

    @ApiModelProperty(value = "排序编号")
    protected Integer sortId;

    @ApiModelProperty(value = "是否启用: ENABLE:已启用; DISABLE:未启用")
    protected Status isEnabled;

    @ApiModelProperty(value = "是否热门: YES:热门; NO:正常")
    protected YesNo isHot;

    @ApiModelProperty(value = "是否推荐: YES:推荐; NO:正常")
    protected YesNo isRecommend;

    @ApiModelProperty(value = "是否置顶: YES:置顶; NO:正常")
    protected YesNo isTop;

    @ApiModelProperty(value = "是否需要授权: YES:需要授权; NO:不需授权")
    protected YesNo isAuthorized;

    @ApiModelProperty(value = "创建时间")
    protected Date cdate;

    @ApiModelProperty(value = "修改时间")
    protected Date mdate;

    @ApiModelProperty(value = "父级类目")
    protected ColumnClasses parent;

    @ApiModelProperty(value = "子类目清单")
    protected List<ColumnClasses> childs;

}