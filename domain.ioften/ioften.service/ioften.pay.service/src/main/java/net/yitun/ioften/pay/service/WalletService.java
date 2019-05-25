package net.yitun.ioften.pay.service;

import com.github.pagehelper.Page;

import net.yitun.ioften.pay.entity.Flows;
import net.yitun.ioften.pay.entity.Wallet;
import net.yitun.ioften.pay.entity.vo.wallet.WalletBoard;
import net.yitun.ioften.pay.model.wallet.WalletAdjust;
import net.yitun.ioften.pay.model.wallet.WalletQuery;

/**
 * <pre>
 * <b>支付 钱包服务.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2018年12月10日 下午5:12:54
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月10日 下午5:12:54 LH
 *         new file.
 * </pre>
 */
public interface WalletService {

    /**
     * 详情信息
     * 
     * @param id ID
     * @return Wallet 详情信息
     */
    Wallet get(Long id);

    /**
     * 分页查询
     * 
     * @param query 筛选参数
     * @return Page<Wallet> 分页结果
     */
    Page<Wallet> query(WalletQuery query);

    /**
     * 账号总览
     * 
     * @param id ID
     * @return Wallet 详情信息
     */
    WalletBoard board(Long id);

    /**
     * 钱包开户
     * 
     * @param uid 用户ID
     * @return Wallet 钱包信息
     */
    Wallet create(Long uid);

    /**
     * 内部调账
     * 
     * @param adjust 调账明细
     * @return Flows 调账流水
     */
    Flows adjust(WalletAdjust adjust);

}
