package net.yitun.ioften.cms.model.conf;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.yitun.basic.Util;

/**
 * <pre>
 * <b>资讯 置顶文章.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月4日 下午4:21:15 LH
 *         new file.
 * </pre>
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TotopItem implements Serializable {

    @NotNull(message = "文章ID无效")
    @Min(value = Util.MIN_ID, message = "文章ID无效")
    @ApiModelProperty(value = "ID, 文章ID", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
    protected Long id;

    @ApiModelProperty(value = "资讯标题", required = true)
    protected String title;

    @ApiModelProperty(value = "作者昵称", required = true)
    protected String author;

    @ApiModelProperty(value = "发布时间", required = true)
    protected Date issueTime;

    /* SVUID */
    private static final long serialVersionUID = 1L;

}
