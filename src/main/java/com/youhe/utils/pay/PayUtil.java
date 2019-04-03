package com.youhe.utils.pay;

import cn.hutool.core.util.ClassUtil;
import com.alibaba.fastjson.JSONObject;
import com.youhe.biz.order.OrderBiz;
import com.youhe.controller.user.shop.index.IndexController;
import com.youhe.entity.order.Order;
import com.youhe.entity.order.OrderDetails;
import com.youhe.entity.pay.Refund;
import com.youhe.exception.YuheOAException;
import com.youhe.utils.pay.sdk.domain.Response;
import com.youhe.utils.pay.sdk.pay.PaymentHelper;
import com.youhe.utils.pay.sdk.pay.domain.cashierPay.CashierRequest;
import com.youhe.utils.pay.sdk.pay.domain.cashierPay.CashierResponse;
import com.youhe.utils.pay.sdk.pay.domain.cashierPay.OrderGoods;
import com.youhe.utils.pay.sdk.pay.domain.cashierPay.OrderInfo;
import com.youhe.utils.pay.sdk.pay.domain.refund.RefundApplyRequest;
import com.youhe.utils.pay.sdk.pay.domain.refund.RefundApplyResponse;
import com.youhe.utils.pay.sdk.utils.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 支付工具类
 */
public class PayUtil {

    private final static Logger LOGGER = LoggerFactory.getLogger(PayUtil.class);

    static {
        Config.initialize(new File(ClassUtil.getClassPath() + "static/payproperties/config_uat.properties"));
    }

    /**
     * 统一下单接口（不包含分账）
     * @param orderNo 订单编号
     * @param payAmount 支付金额，单位：分
     * @param orderDetailsList 订单详情列表
     * @return 收银台订购地址URL
     */
    public static String cashierPay(String orderNo, Long payAmount, List<OrderDetails> orderDetailsList) {
        try {
            String customerCode = Config.getCustomerCode(); //商户编号
            String terminalNo = "10001"; //终端号，写死
            String clientIp = "127.0.0.1"; //IP
            String payCurrency = "CNY"; //币种，写死
            String channelType = "01"; //渠道类型，写死

            String notifyUrl = "http://www.baidu.com"; //异步通知地址
            String redirectUrl = "http://238r9j8196.wicp.vip/touristShop/asynchronousPay"; //同步通知地址
            String attachData = payAmount + ""; //备注数据,可空
            String transactionStartTime = Config.getTransactionStartTime();  //交易开始时间
            String transactionEndTime = ""; // 交易结束时间

            OrderInfo orderInfo = new OrderInfo();
//            orderInfo.setBusinessType("test");
            orderDetailsList.forEach(orderDetails -> {
                orderInfo.setId(String.valueOf(orderDetails.getId()));
                orderInfo.addGood(new OrderGoods(orderDetails.getName(), String.valueOf(orderDetails.getNum()), orderDetails.getPirce()));
            });

            CashierRequest order = new CashierRequest(orderNo, customerCode, terminalNo, clientIp, orderInfo,
                    payAmount, payCurrency, channelType, notifyUrl, redirectUrl,
                    attachData, transactionStartTime, transactionEndTime);

            //----分账设置，如需分账，一定要传true
            order.setNeedSplit(false);
            //是否限制信用卡支付，不填默认是false(即不限制)
            order.setNoCreditCards(true);
            CashierResponse response = PaymentHelper.cashierPay(order);

            if (response.getReturnCode().equals("0000")) {
                LOGGER.info("收银台订购地址：{}", response.getCasherUrl());
                return response.getCasherUrl();
            } else {
                LOGGER.error("订单{}下单失败：{}", orderNo, response.getReturnMsg());
                throw new YuheOAException("订单" + orderNo + "下单失败" + response.getReturnMsg());
            }
        } catch (Exception e) {
            throw new YuheOAException("统一下单异常：" + e.getMessage());
        }

    }


    public static Response refundApply(Refund refund) throws Exception {



        /*
        String transactionNo = "";//原交易单号
        String outTradeNo = "sd5fds4f65ds065064065012";//原支付交易的商户订单号
        String customerCode = "5651300000002202";//商户编号
        long amount = 1;//原订单支付金额
        String notifyUrl = "www.baidu.com";//退款结果通知
        String sourceChannel = "API";//渠道类型
        String outRefundNo = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());//商户退款单号
        long refundAmount = 1;//申请退款金额
        String remark = "";//退款申请说明
        Long userId = null;//门户的用户ID，对API可空
*/

        System.out.println(Config.getCustomerCode());
        RefundApplyRequest order = new RefundApplyRequest(refund.getTransactionNo(), refund.getOutTradeNo(), Config.getCustomerCode(), refund.getAmount(),
                refund.getNotifyUrl(), refund.getSourceChannel(), "RF-" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + (int) ((Math.random() * 9 + 1) * 10000)
                , refund.getRefundAmount(),
                refund.getRemark(), refund.getUserId());

        RefundApplyResponse response = PaymentHelper.refundApply(order);
        System.out.println("交易结果：" + JSONObject.toJSONString(response));

        return response;
    }
}
