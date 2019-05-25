package net.yitun.ioften.pay.model.rank;

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
import net.yitun.ioften.crm.model.user.UserDetail;

/**
 * <pre>
 * <b>支付 服豆排名.</b>
 * <b>Description:</b>
 *
 * <b>Author:</b> LH
 * <b>Date:</b> 2018年12月10日 下午4:47:12
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月10日 下午4:47:12 LH
 *         new file.
 * </pre>
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = { "id" })
public class RankDetail implements Serializable {

    @ApiModelProperty(value = "ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
    protected Long id;

    @ApiModelProperty(value = "用户ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
    protected Long userId;

    @ApiModelProperty(value = "排名")
    protected Integer no;

    @ApiModelProperty(value = "累计收益")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
    protected Long amount;

    @ApiModelProperty(value = "创建时间")
    protected Date ctime;

    @ApiModelProperty(value = "修改时间")
    protected Date mtime;

    @ApiModelProperty(value = "用户资料")
    protected UserDetail user;

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public RankDetail(Long id, Long userId, Integer no, Long amount, Date ctime, Date mtime) {
        super();
        this.id = id;
        this.userId = userId;
        this.no = no;
        this.amount = amount;
        this.ctime = ctime;
        this.mtime = mtime;
    }

}
