package net.yitun.ioften.cms.model.conf;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
 * <b>资讯 关注话题.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月4日 下午4:16:40 LH
 *         new file.
 * </pre>
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TopicItem implements Serializable {

    @NotNull(message = "话题ID无效")
    @Min(value = Util.MIN_ID, message = "话题ID无效")
    @ApiModelProperty(value = "ID, 类目ID", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
    protected Long id;

    @NotNull(message = "话题(类目)无效")
    @Size(min = 2, max = 32, message = "话题(类目)长度为2~16字符")
    @ApiModelProperty(value = "话题(类目)", required = true)
    protected String name;

    /* SVUID */
    private static final long serialVersionUID = 1L;

}
