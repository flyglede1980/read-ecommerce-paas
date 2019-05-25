package net.yitun.ioften.pay.model.wallet;

import javax.validation.constraints.Min;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.yitun.basic.Util;
import net.yitun.basic.model.Page;

/**
 * <pre>
 * <b>支付 账户分页查询.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2018年12月14日 上午11:41:33
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月14日 上午11:41:33 LH
 *         new file.
 * </pre>
 */
@Setter
@Getter
@ToString
public class WalletQuery extends Page {

    @ApiModelProperty(value = "ID, 用户ID，也是钱包ID")
    @Min(value = Util.MIN_ID, message = "ID无效")
    protected Long id;

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public WalletQuery() {
        super(null, null, null, "t1.id desc");
    }

}
