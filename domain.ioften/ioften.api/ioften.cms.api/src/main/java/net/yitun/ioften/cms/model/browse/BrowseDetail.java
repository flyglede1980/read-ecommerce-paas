package net.yitun.ioften.cms.model.browse;

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
import net.yitun.basic.dict.YesNo;
import net.yitun.ioften.cms.model.show.NewsList;

/**
 * <pre>
 * <b>资讯 浏览记录信息.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2018年12月12日 下午1:20:26
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月12日 下午1:20:26 LH
 *         new file.
 * </pre>
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = { "id" })
public class BrowseDetail implements Serializable {

    @ApiModelProperty(value = "ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
    protected Long id;

    @ApiModelProperty(value = "用户ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
    protected Long uid;

    @ApiModelProperty(value = "资讯ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
    protected Long aid;

    @ApiModelProperty(value = "是否阅读, NO:否; YES:是")
    protected YesNo view;

    @ApiModelProperty(value = "是否点赞, NO:否; YES:是")
    protected YesNo enjoy;

    @ApiModelProperty(value = "是否收藏, NO:否; YES:是")
    protected YesNo favorite;

    @ApiModelProperty(value = "是否评论, NO:否; YES:是")
    protected YesNo comment;

    @ApiModelProperty(value = "挖矿金额")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
    protected Long award;

    @ApiModelProperty(value = "阅读挖矿金额")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
    protected Long viewAward;

    @ApiModelProperty(value = "点赞时间")
    protected Date enjoyTime;

    @ApiModelProperty(value = "点赞挖矿金额")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
    protected Long enjoyAward;

    @ApiModelProperty(value = "打赏金额, 0表示未打赏")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
    protected Long reward;

    @ApiModelProperty(value = "创建时间")
    protected Date ctime;

    @ApiModelProperty(value = "修改时间")
    protected Date mtime;

    @ApiModelProperty(value = "资讯简要信息")
    protected NewsList news;

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public BrowseDetail(Long id, Long uid, Long aid, YesNo view, YesNo enjoy, YesNo favorite, YesNo comment, Long award,
            Long viewAward, Date enjoyTime, Long enjoyAward, Long reward, Date ctime, Date mtime) {
        super();
        this.id = id;
        this.uid = uid;
        this.aid = aid;
        this.view = view;
        this.enjoy = enjoy;
        this.favorite = favorite;
        this.comment = comment;
        this.award = award;
        this.viewAward = viewAward;
        this.enjoyTime = enjoyTime;
        this.enjoyAward = enjoyAward;
        this.reward = reward;
        this.ctime = ctime;
        this.mtime = mtime;
    }

}
