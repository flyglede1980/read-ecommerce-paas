package net.yitun.ioften.cms.model.card;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 卡种信息对外业务实体
 * @since 1.0.0
 * @see Data
 * @see ToString
 * @see AllArgsConstructor
 * @see NoArgsConstructor
 * @see EqualsAndHashCode
 * @author Flyglede
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"cardTypeId"})
public class CardTypeOutline implements Serializable{
    @ApiModelProperty(value = "卡种编号")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
    protected Long cardTypeId;
    @ApiModelProperty(value = "卡种名称")
    protected String name;
    @ApiModelProperty(value = "卡种图标,指示图标存储的地址/文件名")
    protected String cardIcon;
    @ApiModelProperty(value = "特权描述图标,指示图标存储的地址/文件名")
    protected String privilegeIcon;
    @ApiModelProperty(value = "身份图标,指示图标存储的地址/文件名")
    protected String identityIcon;
    @ApiModelProperty(value = "卡种描述")
    protected String description;
    @ApiModelProperty(value = "卡种单价")
    protected BigDecimal price;
    @ApiModelProperty(value = "有效期,单位为月")
    protected Integer period;
}