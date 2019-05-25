package net.yitun.ioften.cms.model.category;

import java.io.Serializable;
import java.util.ArrayList;
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
import net.yitun.basic.dict.Status;
import net.yitun.basic.dict.YesNo;

/**
 * <pre>
 * <b>类目详细.</b>
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
public class CategoryDetail implements Serializable {

    @ApiModelProperty(value = "分类编号")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
    protected Long classId;

    @ApiModelProperty(value = "创建者")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
    protected Long usersId;

    @ApiModelProperty(value = "上级分类ID, 默认:0")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
    protected Long parentId;

    @ApiModelProperty(value = "分类代码")
    protected String code;

    @ApiModelProperty(value = "分类名称, 长度为2~16个字符")
    protected String name;

    @ApiModelProperty(value = "级数, 默认:1")
    protected Integer level;

    @ApiModelProperty(value = "关系")
    protected String relation;

    @ApiModelProperty(value = "分类图标")
    protected String icon;

    @ApiModelProperty(value = "分类描述")
    protected String description;

    @ApiModelProperty(value = "顺序, 默认: 0")
    protected Integer sortId;

    @ApiModelProperty(value = "状态, DISABLE:禁用; ENABLE:正常")
    protected Status isEnabled;

    @ApiModelProperty(value = "是否热门: YES:热门; NO:正常")
    protected YesNo isHot;

    @ApiModelProperty(value = "是否推荐:YES:推荐; NO:正常")
    protected YesNo isRecommend;

    @ApiModelProperty(value = "是否置顶:YES:置顶; NO:正常")
    protected YesNo isTop;

    @ApiModelProperty(value = "是否需要授权:NO:不需授权;YES:需要授权")
    protected YesNo isAuthorized;

    @ApiModelProperty(value = "创建时间")
    protected Date cDate;

    @ApiModelProperty(value = "修改时间")
    protected Date mDate;

    @JSONField(ordinal = 999)
    @ApiModelProperty(value = "父类目")
    protected CategoryDetail parent;

    @JSONField(ordinal = 1000)
    @ApiModelProperty(value = "子类目清单")
    protected List<CategoryDetail> childs;

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public CategoryDetail(Long classId) {
        super();
        this.classId = classId;
    }

    public CategoryDetail(Long classId, Long parentId, String name, Integer level, String icon, String description, Integer sortId, Status isEnabled, Date cDate, Date mDate) {
        this.classId = classId;
        this.parentId = parentId;
        this.name = name;
        this.level = level;
        this.icon = icon;
        this.description = description;
        this.sortId = sortId;
        this.isEnabled = isEnabled;
        this.cDate = cDate;
        this.mDate = mDate;
    }

/**
     * 添加子节点
     *
     * @param child
     * @return CategoryDetail
     */
    public CategoryDetail addChild(CategoryDetail child) {
        if (null == child)
            return this;
        if (null == this.childs)
            this.childs = new ArrayList<>();
        this.childs.add(child);
        return this;
    }
}