package net.yitun.ioften.crm.model.mesg;

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
import net.yitun.ioften.crm.dicts.MesgType;
import net.yitun.ioften.crm.model.user.UserDetail;

/**
 * <pre>
 * <b>用户 消息详情.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2018年12月17日 下午1:29:38
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月17日 下午1:29:38 LH
 *         new file.
 * </pre>
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = { "id" })
public class MesgDetail implements Serializable {

    @ApiModelProperty(value = "ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
    protected Long id;

    @ApiModelProperty(value = "用户ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
    protected Long uid;

    @ApiModelProperty(value = "类型, SYS:系统; ENJOY:获赞; FOCUS:关注")
    protected MesgType type;

    @ApiModelProperty(value = "参与者, 0:系统; 其他为用户ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
    protected Long actor;

    @ApiModelProperty(value = "关联对象, 如:0:未知,文章ID,订单ID,评论ID,用户ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
    protected Long target;

    @ApiModelProperty(value = "内容")
    protected String content;

    @ApiModelProperty(value = "次数, 仅type=ENJOY")
    protected Integer times;

    @ApiModelProperty(value = "状态, 未阅读, 已阅读")
    protected YesNo status;

    @ApiModelProperty(value = "创建时间")
    protected Date ctime;

    @ApiModelProperty(value = "修改时间")
    protected Date mtime;

    @ApiModelProperty(value = "参与者信息")
    protected UserDetail actorUser;

    @ApiModelProperty(value = "涉及资源对象")
    protected Object targetRefer;

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public MesgDetail(Long id, Long uid, MesgType type, Long actor, Long target, String content, Integer times, YesNo status,
            Date ctime, Date mtime) {
        super();
        this.id = id;
        this.uid = uid;
        this.type = type;
        this.actor = actor;
        this.target = target;
        this.content = content;
        this.times = times;
        this.status = status;
        this.ctime = ctime;
        this.mtime = mtime;
    }

}
