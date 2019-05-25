package net.yitun.ioften.cms.entity.card;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 卡种信息(与会员卡独立)
 * @since 1.0.0
 * @see ApiModelProperty
 * @see AllArgsConstructor
 * @see Data
 * @see NoArgsConstructor
 * @see ToString
 * @author Flyglede
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Cardtypes {
    @ApiModelProperty(value = "卡种编号")
    protected Long cardTypeId;
    @ApiModelProperty(value = "用户编号,会员卡创建人")
    protected Long userId;
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
    @ApiModelProperty(value = "是否允许转赠:0--禁止转赠;1--允许转赠")
    protected Integer isGive;
    @ApiModelProperty(value = "是否允许升级:0--禁止升级;1--允许升级")
    protected Integer isUpgrade;
    @ApiModelProperty(value = "是否允许延期:0--禁止延期;1--允许延期")
    protected Integer isDelay;
    @ApiModelProperty(value = "使用优先级(数字越大,越有限使用),针对同一用户购买多张会员卡情况使用")
    protected Integer priority;
    @ApiModelProperty(value = "排列序号")
    protected Integer orderID;
    @ApiModelProperty(value = "启用状态:0--禁用;1--启用")
    protected Integer status;
    @ApiModelProperty(value = "创建时间")
    protected Date cTime;
    @ApiModelProperty(value = "变更时间")
    protected Date uTime;
}