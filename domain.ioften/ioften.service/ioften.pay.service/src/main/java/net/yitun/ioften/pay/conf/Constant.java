package net.yitun.ioften.pay.conf;

/**
 * <pre>
 * <b>PAY 常量.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年8月22日 下午3:02:19 LH
 *         new file.
 * </pre>
 */
public class Constant {

    /** 汇率配置 */
    public static final String RATE_CONF = "pay.rate";
    /** 挖矿配置 */
    public static final String GAIN_CONF = "pay.gain";
    // =================================================================

    /** CKEY: 汇率缓存 */
    public static final String CKEY_RATE = "pay.rate#35#5"; // 缓存35s, 剩5s时自动刷新
    /** CKEY: 排名缓存 */
    public static final String CKEY_RANK = "pay.rank#86400#-1"; // 缓存24H
    /** CKEY: 挖矿配置缓存 */
    public static final String CKEY_GAIN_CONF = "pay.gain#3600#-1"; // 缓存1H

    /** CKEY: 钱包账户缓存 */
    public static final String CKEY_WALLET = "pay.wallet:my#60#-1"; // 缓存60s
    /** CKEY: 钱包账户缓存 */
    public static final String CKEY_WALLET_BOARD = "pay.wallet:myboard#120#-1"; // 缓存120s
    /** CKEY: 我的流水缓存 */
    public static final String CKEY_FLOWS_MY = "pay.flows:my#172800#-1"; // 缓存48H
    // =================================================================

    /** CNY/SDC 汇率接口 */ // https://api.silktrader.net/market/exchange-rate/cny/sdc
    public static final String API_CNY_SDC_RATE = "https://api.silktrader.net/market/exchange-rate/cny/sdc";
    /** USD/CNY 汇率接口 */ // https://ws.silktrader.net/market/exchange-rate/usd-cny
    public static final String API_USD_CNY_RATE = "https://api.silktrader.net/market/exchange-rate/usd-cny";
    /** USD/SDC 汇率接口 */ // https://api.silktrader.net/market/exchange-rate/usd/sdc
    public static final String API_USD_SDC_RATE = "https://api.silktrader.net/market/exchange-rate/usd/sdc";
    // =================================================================

}
