package net.yitun.ioften.pay.model.wallet;

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
import net.yitun.basic.dict.Status;
import net.yitun.ioften.crm.model.user.UserDetail;

/**
 * <pre>
 * <b>支付 钱包账户.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2018年12月10日 下午4:56:18
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月10日 下午4:56:18 LH
 *         new file.
 * </pre>
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = { "id" })
public class WalletDetail implements Serializable {

    @ApiModelProperty(value = "ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
    protected Long id;

    @ApiModelProperty(value = "积分赠送部")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
    protected Long give;

    @ApiModelProperty(value = "积分收益部分")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
    protected Long income;

    @ApiModelProperty(value = "积分预存部分")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
    protected Long balance;

    @ApiModelProperty(value = "累计积分收益, 范围: 0~1844 6744 0737.09551615")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
    protected Long totalIncome;

    @ApiModelProperty(value = "累计RMB充值, 范围: 0~1844 6744 0737.09551615")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
    protected Long totalAddedRmb;

    @ApiModelProperty(value = "贡献值")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
    protected Long contribution;

    @ApiModelProperty(value = "区块地址")
    protected String blockAddr;

    @ApiModelProperty(value = "状态, 禁用; 正常")
    protected Status status;

    @ApiModelProperty(value = "创建时间")
    protected Date ctime;

    @ApiModelProperty(value = "修改时间")
    protected Date mtime;

    @ApiModelProperty(value = "用户资料")
    protected UserDetail user;

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public WalletDetail(Long id, Long give, Long income, Long balance, Long totalIncome, Long totalAddedRmb, Long contribution,
            String blockAddr, Status status, Date ctime, Date mtime) {
        super();
        this.id = id;
        this.give = give;
        this.income = income;
        this.balance = balance;
        this.totalIncome = totalIncome;
        this.totalAddedRmb = totalAddedRmb;
        this.contribution = contribution;
        this.blockAddr = blockAddr;
        this.status = status;
        this.ctime = ctime;
        this.mtime = mtime;
    }

}
