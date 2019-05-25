package net.yitun.ioften.order.website.action;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import net.yitun.ioften.cms.CardApi;
import net.yitun.ioften.cms.model.card.*;
import net.yitun.ioften.order.OrderApi;
import net.yitun.ioften.order.conf.AlipayConfig;
import net.yitun.ioften.order.entity.cards.Orders;
import net.yitun.ioften.order.model.cardorder.CardOrderCreate;
import net.yitun.ioften.order.service.OrderService;
import net.yitun.basic.model.Result;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alipay.api.AlipayConstants;
import com.alibaba.fastjson.JSON;
import net.yitun.ioften.order.util.OrderNoCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 卡种信息、特权信息、卡种特权信息服务控制层服务
 * @since 1.0.0
 * @see Api
 * @see RestController
 * @see ApiOperation
 * @see ApiImplicitParam
 * @see RequestParam
 * @see Validated
 * @see RequestBody
 * @see CardApi
 * @see CardTypeOutline
 * @see Cardtypes
 * @see CardTypeDetail
 * @see CardprivilegeBO
 * @see PrivilegeDetail
 * @see Privilegeset
 * @see CardTypeCreate
 * @see CardTypeModify
 * @author Flyglede
 * @date 2019-01-10
 */
@Api(tags = "订单服务接口")
@RestController("order.OrderApi")
public class OrderAction implements OrderApi {
    /**订单服务接口*/
    @Autowired
    protected OrderService orderService;

    /**
     * 创建会员卡订单
     * @param model--会员卡订单信息
     * @return 创建操作结果
     */
    @Override
    public Result<String> createCardOrder(CardOrderCreate model) {
        Orders orders = new Orders();
        String strOrderNo = "";
        BigDecimal dealMoney = new BigDecimal("0.00");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        strOrderNo = OrderNoCreator.getOrderNo(20);
        orders.set
        log.info("==================支付宝下单,订单号为:" + orders.getOrderNo());
        Result<Orders> result = this.orderService.createCardOrder(model);
        if(null != result && result.ok()) {
            strTradeNo = result.getData().getOrderNo();
            dealMoney = result.getData().getDealMoney();
        }
    }

    /**
     * 支付宝支付成功完成后，异步请求该接口
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @Override
    public Result<String> notify(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("==================支付宝异步返回支付结果开始");
        Map<String, String[]> aliParams = request.getParameterMap();
        //用以存放转化后的参数集合
        Map<String, String> conversionParams = new HashMap<String, String>();
        for (Iterator<String> iter = aliParams.keySet().iterator(); iter.hasNext();) {
            String key = iter.next();
            String[] values = aliParams.get(key);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            conversionParams.put(key, valueStr);
        }
        logger.info("==================返回参数集合："+conversionParams);
        String status = notify(conversionParams);
        return status;
    }

    private String notify(Map<String, String> conversionParams){
        logger.info("==================支付宝异步请求逻辑处理");
        //签名验证(对支付宝返回的数据验证，确定是支付宝返回的)
        boolean signVerified = false;
        try {
            //调用SDK验证签名
            signVerified = AlipaySignature.rsaCheckV1(conversionParams, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, AlipayConfig.SIGNTYPE);

        } catch (AlipayApiException e) {
            logger.info("==================验签失败 ！");
            e.printStackTrace();
        }
        //对验签进行处理
        if (signVerified) {
            //验签通过
            //获取需要保存的数据
            String appId=conversionParams.get("app_id");//支付宝分配给开发者的应用Id
            String notifyTime=conversionParams.get("notify_time");//通知时间:yyyy-MM-dd HH:mm:ss
            String gmtCreate=conversionParams.get("gmt_create");//交易创建时间:yyyy-MM-dd HH:mm:ss
            String gmtPayment=conversionParams.get("gmt_payment");//交易付款时间
            String gmtRefund=conversionParams.get("gmt_refund");//交易退款时间
            String gmtClose=conversionParams.get("gmt_close");//交易结束时间
            String tradeNo=conversionParams.get("trade_no");//支付宝的交易号
            String outTradeNo = conversionParams.get("out_trade_no");//获取商户之前传给支付宝的订单号（商户系统的唯一订单号）
            String outBizNo=conversionParams.get("out_biz_no");//商户业务号(商户业务ID，主要是退款通知中返回退款申请的流水号)
            String buyerLogonId=conversionParams.get("buyer_logon_id");//买家支付宝账号
            String sellerId=conversionParams.get("seller_id");//卖家支付宝用户号
            String sellerEmail=conversionParams.get("seller_email");//卖家支付宝账号
            String totalAmount=conversionParams.get("total_amount");//订单金额:本次交易支付的订单金额，单位为人民币（元）
            String receiptAmount=conversionParams.get("receipt_amount");//实收金额:商家在交易中实际收到的款项，单位为元
            String invoiceAmount=conversionParams.get("invoice_amount");//开票金额:用户在交易中支付的可开发票的金额
            String buyerPayAmount=conversionParams.get("buyer_pay_amount");//付款金额:用户在交易中支付的金额
            String tradeStatus = conversionParams.get("trade_status");// 获取交易状态
            //支付宝官方建议校验的值（out_trade_no、total_amount、sellerId、app_id）
            AlipaymentOrder alipaymentOrder=this.selectByOutTradeNo(outTradeNo);

            if(alipaymentOrder!=null&&totalAmount.equals(alipaymentOrder.getTotalAmount().toString())&&AlipayConfig.APPID.equals(appId)){
                //修改数据库支付宝订单表(因为要保存每次支付宝返回的信息到数据库里，以便以后查证)
                alipaymentOrder.setNotifyTime(dateFormat(notifyTime));
                alipaymentOrder.setGmtCreate(dateFormat(gmtCreate));
                alipaymentOrder.setGmtPayment(dateFormat(gmtPayment));
                alipaymentOrder.setGmtRefund(dateFormat(gmtRefund));
                alipaymentOrder.setGmtClose(dateFormat(gmtClose));
                alipaymentOrder.setTradeNo(tradeNo);
                alipaymentOrder.setOutBizNo(outBizNo);
                alipaymentOrder.setBuyerLogonId(buyerLogonId);
                alipaymentOrder.setSellerId(sellerId);
                alipaymentOrder.setSellerEmail(sellerEmail);
                alipaymentOrder.setTotalAmount(Double.parseDouble(totalAmount));
                alipaymentOrder.setReceiptAmount(Double.parseDouble(receiptAmount));
                alipaymentOrder.setInvoiceAmount(Double.parseDouble(invoiceAmount));
                alipaymentOrder.setBuyerPayAmount(Double.parseDouble(buyerPayAmount));
                switch (tradeStatus) // 判断交易结果
                {
                    case "TRADE_FINISHED": // 交易结束并不可退款
                        alipaymentOrder.setTradeStatus((byte) 3);
                        break;
                    case "TRADE_SUCCESS": // 交易支付成功
                        alipaymentOrder.setTradeStatus((byte) 2);
                        break;
                    case "TRADE_CLOSED": // 未付款交易超时关闭或支付完成后全额退款
                        alipaymentOrder.setTradeStatus((byte) 1);
                        break;
                    case "WAIT_BUYER_PAY": // 交易创建并等待买家付款
                        alipaymentOrder.setTradeStatus((byte) 0);
                        break;
                    default:
                        break;
                }
                int returnResult=this.updateByPrimaryKey(alipaymentOrder);    //更新交易表中状态
                if(tradeStatus.equals("TRADE_SUCCESS")) {    //只处理支付成功的订单: 修改交易表状态,支付成功
                    if(returnResult>0){
                        return "success";
                    }else{
                        return "fail";
                    }
                }else{
                    return "fail";
                }
            }else{
                logger.info("==================支付宝官方建议校验的值（out_trade_no、total_amount、sellerId、app_id）,不一致！返回fail");
                return"fail";
            }
        } else {  //验签不通过
            logger.info("==================验签不通过 ！");
            return "fail";
        }
    }
}