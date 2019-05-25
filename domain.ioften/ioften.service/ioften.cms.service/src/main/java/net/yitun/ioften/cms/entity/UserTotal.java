package net.yitun.ioften.cms.entity;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * <pre>
 * <b>用户信息.</b>
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
@EqualsAndHashCode(of = { "id" })
public class UserTotal implements Serializable {

    @ApiModelProperty(value = "用户ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
    protected Long id;

    @ApiModelProperty(value = "昵称")
    protected String nick;

    @ApiModelProperty(value = "简介")
    protected String intro;

    @ApiModelProperty(value = "资讯总数")
    protected Integer articleTotal;

    @ApiModelProperty(value = "总关注数")
    protected Integer careTotal;

    @ApiModelProperty(value = "总粉丝数")
    protected Integer fansTotal;

    @ApiModelProperty(value = "总获赏数")
    protected Long rewardTotal;

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public UserTotal(Long id) {
        super();
        this.id = id;
    }
}
