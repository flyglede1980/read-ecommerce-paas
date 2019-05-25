package net.yitun.ioften.cms.model.comment;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.yitun.ioften.crm.dicts.Level;
import net.yitun.ioften.crm.dicts.Type;

/**
 * @Author ：ZH.
 * @Date ：Created in 15:31 2018/12/20 0020
 * @Description：被评论用户信息
 * @Modified By：
 * @Version: 0.1
 **/
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ApiModel(value = "被评论用户信息")
public class CommentUser implements Serializable {

    @ApiModelProperty(value = "评论ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
    protected Long id;

    @ApiModelProperty(value = "评论用户ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
    protected Long uid;

    @ApiModelProperty(value = "评论内容, 评论最多1024个字符")
    protected String content;

    @ApiModelProperty(value = "评论用户昵称, 长度为2~16个字符")
    protected String nick;

    @ApiModelProperty(value = "评论用户类型, N:无, PE:个人, EN:企业, EW:长见号")
    protected Type type;

    @ApiModelProperty(value = "评论用户等级, N, VIP1, VIP2, ....")
    protected Level level;

    /* SVUID */
    private static final long serialVersionUID = 1L;
}
