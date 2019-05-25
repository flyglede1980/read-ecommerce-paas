package net.yitun.ioften.cms.model.category;

import java.io.Serializable;
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
import net.yitun.basic.dict.YesNo;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"classId"})
public class ColumnClassesDetail implements Serializable {

    @ApiModelProperty(value = "分类编号")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
    protected Long classId;

    @ApiModelProperty(value = "创建者")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
    protected Long usersId;

    @ApiModelProperty(value = "上级分类")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
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

    @JSONField(ordinal = 999)
    @ApiModelProperty(value = "父级类目")
    protected ColumnClassesDetail parent;

    @JSONField(ordinal = 1000)
    @ApiModelProperty(value = "子类目清单")
    protected List<ColumnClassesDetail> childs;

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public ColumnClassesDetail(Long classId) {
        super();
        this.classId = classId;
    }

    public ColumnClassesDetail(Long classId, Long parentId, String code, String name, String icon, String description, Integer sortId) {
        this.classId = classId;
        this.parentId = parentId;
        this.code = code;
        this.name = name;
        this.icon = icon;
        this.description = description;
        this.sortId = sortId;
    }
}
