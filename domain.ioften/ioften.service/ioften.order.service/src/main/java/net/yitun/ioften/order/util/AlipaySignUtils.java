package net.yitun.ioften.order.util;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import lombok.extern.slf4j.Slf4j;
import net.yitun.ioften.order.conf.AlipayConfig;
import net.yitun.ioften.order.entity.cards.Orders;

/**
 * Created by Administrator on 2019/1/12.
 */
@Slf4j
public class AlipaySignUtils {
    public String getAlipayOrderSignData(Orders orders) {
        String strSignOrderData = "";
        log.info("==================支付宝下单,订单号为:" + orders.getOrderNo());
        try {
            AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.ALIPAY_GATEWAY, AlipayConfig.ALIPAY_APPID, AlipayConfig.ALIPAY_PRIVATEKEY,
                    AlipayConfig.ALIPAY_FORMAT, AlipayConfig.ALIPAY_CHARSET, AlipayConfig.ALIPAY_PUBLICKEY, AlipayConfig.ALIPAY_SIGNTYPE);
            AlipayTradeAppPayRequest alipayRequest = new AlipayTradeAppPayRequest();
            AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
            model.setBody("会员购卡订单");
            model.setSubject("会员卡");
            model.setOutTradeNo(orders.getOrderNo());
            model.setTotalAmount(orders.getDealMoney().toString());
            model.setProductCode("QUICK_MSECURITY_PAY");
            alipayRequest.setNotifyUrl(AlipayConfig.ALIPAY_NOTIFY_URL);
            alipayRequest.setReturnUrl(AlipayConfig.ALIPAY_RETURN_URL);
            AlipayTradeAppPayResponse alipayResponse = alipayClient.sdkExecute(alipayRequest);
            strSignOrderData = alipayResponse.getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
            log.info("与支付宝交互出错,生成订单失败,请检查代码!");
        }
        return strSignOrderData;
    }
}
