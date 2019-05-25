package net.yitun.ioften.pay;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import net.yitun.basic.model.PageResult;
import net.yitun.basic.model.Result;
import net.yitun.ioften.pay.conf.Conf;
import net.yitun.ioften.pay.dicts.Channel;
import net.yitun.ioften.pay.dicts.Currency;
import net.yitun.ioften.pay.dicts.Way;
import net.yitun.ioften.pay.model.flows.FlowsDetail;
import net.yitun.ioften.pay.model.wallet.MyWallet;
import net.yitun.ioften.pay.model.wallet.WalletAdjust;
import net.yitun.ioften.pay.model.wallet.WalletDetail;
import net.yitun.ioften.pay.model.wallet.WalletQuery;

/**
 * <pre>
 * <b>支付 钱包接口.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月6日 下午12:43:11 LH
 *         new file.
 * </pre>
 */
public interface WalletApi {

    /**
     * 账户信息
     * 
     * @return Result<WalletDetail>
     */
    @GetMapping(value = Conf.ROUTE //
            + "/wallet/my", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<WalletDetail> my();

    /**
     * 账户总览
     * 
     * @return Result<MyWallet>
     */
    @GetMapping(value = Conf.ROUTE //
            + "/wallet/myboard", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<MyWallet> board();

    /**
     * 账户详细
     * 
     * @param id 帐户或用户ID
     * @return WalletDetail 账户详细
     */
    @GetMapping(value = Conf.ROUTE //
            + "/wallet/{id}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<WalletDetail> detail(@PathVariable("id") Long id);

    /**
     * 分页查询
     * 
     * @param query 筛选参数
     * @return PageResult<WalletDetail> 分页列表
     */
    @GetMapping(value = Conf.ROUTE //
            + "/wallets", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    PageResult<WalletDetail> query(WalletQuery walletQuery);

    /**
     * 支付调账
     * 
     * @param income 扣款信息
     * @return Result<FlowsDetail> 调账流水
     */
    Result<FlowsDetail> adjust(@RequestBody WalletAdjust adjust);

    /**
     * 支付调账
     * 
     * @param uid      用户ID
     * @param way      流水用途无效
     * @param amount   金额无效
     * @param currency 币种无效
     * @param target   交易对象无效
     * @param channel  支付通道无效
     * @param outFlows 外部流水号, 长度: 0~64个字符
     * @param remark   备注信息, 文章名, 用户名, 商品清单, 长度: 0~64个字符
     * @return Result<FlowsDetail>
     */
    Result<FlowsDetail> adjust(@RequestParam("uid") Long uid, @RequestParam("way") Way way, @RequestParam("amount") Long amount,
            @RequestParam("currency") Currency currency, @RequestParam("target") Long target,
            @RequestParam("channel") Channel channel, @RequestParam("outFlows") String outFlows,
            @RequestParam("remark") String remark);

}
