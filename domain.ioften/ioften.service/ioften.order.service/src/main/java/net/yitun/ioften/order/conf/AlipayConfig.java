package net.yitun.ioften.order.conf;

/**
 * 支付宝支付的基本配置
 * @since 1.0.0
 * @author Flyglede
 * @date 2019-01-12
 */
public class AlipayConfig {
    /**支付宝网关地址*/
    public static String ALIPAY_GATEWAY = "https://openapi.alipay.com/gateway.do";
    /**商户APPID*/
    public static String ALIPAY_APPID = "2018122462699499";
    /**pkcs8格式的私钥*/
    public static String ALIPAY_PRIVATEKEY = "";
    /**返回格式*/
    public static String ALIPAY_FORMAT = "json";
    /**编码格式*/
    public static String ALIPAY_CHARSET = "UTF-8";
    /**支付宝公钥*/
    public static String ALIPAY_PUBLICKEY = "";
    /**签名方式*/
    public static String ALIPAY_SIGNTYPE = "RSA2";
    /**异步回调地址*/
    public static String ALIPAY_NOTIFY_URL = "";
    /**同步回调(APP)地址*/
    public static String ALIPAY_RETURN_URL = "";
}
