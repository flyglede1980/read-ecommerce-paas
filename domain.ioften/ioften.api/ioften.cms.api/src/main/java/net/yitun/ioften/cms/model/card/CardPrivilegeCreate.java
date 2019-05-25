package net.yitun.ioften.cms.model.card;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * 卡种特权信息创建模型
 * @since 1.0.0
 * @see Data
 * @see ToString
 * @see AllArgsConstructor
 * @see NoArgsConstructor
 * @see ApiModelProperty
 * @author Flyglede
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CardPrivilegeCreate {
    @ApiModelProperty(value = "特权编号")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
    protected Long privilegeId;
    @ApiModelProperty(value = "是否开启:0--关闭;1--开启")
    protected Integer isSwitch;
    @ApiModelProperty(value = "特权值,如不填写,默认为0")
    protected Integer privilegeValue;
}