package net.yitun.ioften.pay.entity.vo.wallet;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.yitun.ioften.pay.entity.Wallet;

/**
 * <pre>
 * <b>支付 账户分页查询Vo.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2018年12月14日 下午3:25:13
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月14日 下午3:25:13 LH
 *         new file.
 * </pre>
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
public class WalletQueryVo extends Wallet {

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public WalletQueryVo(Long id) {
        this.id = id;
    }

}
