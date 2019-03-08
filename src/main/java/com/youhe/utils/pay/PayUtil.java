package com.youhe.utils.pay;

import com.alibaba.fastjson.JSONObject;
import com.youhe.controller.user.shop.index.IndexController;
import com.youhe.entity.pay.Refund;
import com.youhe.utils.pay.sdk.domain.Response;
import com.youhe.utils.pay.sdk.pay.PaymentHelper;
import com.youhe.utils.pay.sdk.pay.domain.refund.RefundApplyRequest;
import com.youhe.utils.pay.sdk.pay.domain.refund.RefundApplyResponse;
import com.youhe.utils.pay.sdk.utils.Config;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PayUtil {

    static {
        Config.initialize(new File(IndexController.class.getClassLoader()
                .getResource("").getPath() + "static/payproperties/config_uat.properties"));

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

        RefundApplyRequest order = new RefundApplyRequest(refund.getTransactionNo(), refund.getOutTradeNo(), Config.getCustomerCode(), refund.getAmount(),
                refund.getNotifyUrl(), refund.getSourceChannel(), refund.getOutRefundNo(), refund.getRefundAmount(),
                refund.getRemark(), refund.getUserId());

        RefundApplyResponse response = PaymentHelper.refundApply(order);
        System.out.println("交易结果：" + JSONObject.toJSONString(response));

        return response;
    }
}
