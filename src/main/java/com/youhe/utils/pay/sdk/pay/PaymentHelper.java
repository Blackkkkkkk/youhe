package com.youhe.utils.pay.sdk.pay;

import java.util.Map;

import com.youhe.utils.pay.sdk.pay.domain.WechatH5.WechatH5Request;
import com.youhe.utils.pay.sdk.pay.domain.WechatH5.WechatH5Response;
import com.youhe.utils.pay.sdk.pay.domain.aliJSAPI.AliJSAPIRequest;
import com.youhe.utils.pay.sdk.pay.domain.aliJSAPI.AliJSAPIResponse;
import com.youhe.utils.pay.sdk.pay.domain.authentication.AuthenticationRequest;
import com.youhe.utils.pay.sdk.pay.domain.authentication.AuthenticationResponse;
import com.youhe.utils.pay.sdk.pay.domain.cashierPay.CashierRequest;
import com.youhe.utils.pay.sdk.pay.domain.cashierPay.CashierResponse;
import com.youhe.utils.pay.sdk.pay.domain.microPay.MicroPayRequest;
import com.youhe.utils.pay.sdk.pay.domain.microPay.MicroPayResponse;
import com.youhe.utils.pay.sdk.pay.domain.nativePay.NativePayRequest;
import com.youhe.utils.pay.sdk.pay.domain.nativePay.NativePayResponse;
import com.youhe.utils.pay.sdk.pay.domain.paymenQuery.PaymentQueryRequest;
import com.youhe.utils.pay.sdk.pay.domain.paymenQuery.PaymentQueryResponse;
import com.youhe.utils.pay.sdk.pay.domain.protocol.ProtocolPayBindCardConfirmRequest;
import com.youhe.utils.pay.sdk.pay.domain.protocol.ProtocolPayBindCardConfirmResponse;
import com.youhe.utils.pay.sdk.pay.domain.protocol.ProtocolPayBindCardRequest;
import com.youhe.utils.pay.sdk.pay.domain.protocol.ProtocolPayBindCardResponse;
import com.youhe.utils.pay.sdk.pay.domain.protocol.ProtocolPayConfirmRequest;
import com.youhe.utils.pay.sdk.pay.domain.protocol.ProtocolPayConfirmResponse;
import com.youhe.utils.pay.sdk.pay.domain.protocol.ProtocolPayRequest;
import com.youhe.utils.pay.sdk.pay.domain.protocol.ProtocolPayResponse;
import com.youhe.utils.pay.sdk.pay.domain.protocol.ProtocolPayUnbindCardRequest;
import com.youhe.utils.pay.sdk.pay.domain.protocol.ProtocolQueryRequest;
import com.youhe.utils.pay.sdk.pay.domain.protocol.ProtocolQueryResponse;
import com.youhe.utils.pay.sdk.pay.domain.refund.RefundApplyRequest;
import com.youhe.utils.pay.sdk.pay.domain.refund.RefundApplyResponse;
import com.youhe.utils.pay.sdk.pay.domain.refundQuery.RefundApplyQueryRequest;
import com.youhe.utils.pay.sdk.pay.domain.refundQuery.RefundApplyQueryResponse;
import com.youhe.utils.pay.sdk.pay.domain.split.SplitRequest;
import com.youhe.utils.pay.sdk.pay.domain.split.SplitResponse;
import com.youhe.utils.pay.sdk.pay.domain.split.SplitResultQueryRequest;
import com.youhe.utils.pay.sdk.pay.domain.split.SplitResultQueryResponse;
import com.youhe.utils.pay.sdk.pay.domain.splitRefund.RefundRequest;
import com.youhe.utils.pay.sdk.pay.domain.splitRefund.RefundResponse;
import com.youhe.utils.pay.sdk.pay.domain.union.UnionPayRequest;
import com.youhe.utils.pay.sdk.pay.domain.union.UnionPayResponse;
import com.youhe.utils.pay.sdk.pay.domain.wechatJSAPI.WechatJSAPIRequest;
import com.youhe.utils.pay.sdk.pay.domain.wechatJSAPI.WechatJSAPIResponse;
import com.youhe.utils.pay.sdk.pay.domain.withdraw.CustomerAccountQueryResp;
import com.youhe.utils.pay.sdk.pay.domain.withdraw.Withdraw4SubMerchantRequest;
import com.youhe.utils.pay.sdk.pay.domain.withdraw.Withdraw4SubMerchantResponse;
import com.youhe.utils.pay.sdk.pay.domain.withdraw.WithdrawQuery4SubMerchantRequest;
import com.youhe.utils.pay.sdk.pay.domain.withdraw.WithdrawQuery4SubMerchantResponse;
import com.youhe.utils.pay.sdk.pay.domain.withdraw.WithdrawToCardQueryResponse;
import com.youhe.utils.pay.sdk.pay.domain.withdraw.WithdrawToCardRequest;
import com.youhe.utils.pay.sdk.pay.domain.withdraw.WithdrawToCardResponse;
import com.youhe.utils.pay.sdk.utils.Config;
import com.youhe.utils.pay.sdk.utils.RemoteInvoker;

public class PaymentHelper {

    /**
     * 支付宝服务窗支付
     *
     * @param object 订单内容
     * @throws Exception
     */
    public static AliJSAPIResponse aliJSAPIPay(AliJSAPIRequest request) throws Exception {
        return RemoteInvoker.invoke(request, Config.getAliJSAPIUrl(), AliJSAPIResponse.class);
    }

    /**
     * 微信公众号支付
     *
     * @param object 订单内容
     * @throws Exception
     */
    public static WechatJSAPIResponse wechatJSAPIPay(WechatJSAPIRequest request) throws Exception {
        return RemoteInvoker.invoke(request, Config.getWechatJSAPIUrl(), WechatJSAPIResponse.class);
    }

    /**
     * 微信H5支付
     *
     * @param object 订单内容
     * @throws Exception
     */
    public static WechatH5Response wechatH5Pay(WechatH5Request request) throws Exception {
        return RemoteInvoker.invoke(request, Config.getWechatH5Url(), WechatH5Response.class);
    }

    /**
     * 主掃支付
     *
     * @param object 订单内容
     * @throws Exception
     */
    public static NativePayResponse nativePay(NativePayRequest request) throws Exception {
        return RemoteInvoker.invoke(request, Config.getNativePayUrl(), NativePayResponse.class);
    }

    /**
     * 被掃支付
     *
     * @param object 订单内容
     * @throws Exception
     */
    public static MicroPayResponse microPay(MicroPayRequest request) throws Exception {
        return RemoteInvoker.invoke(request, Config.getScaningURL(), MicroPayResponse.class);
    }

    /**
     * 預下單支付/收銀檯支付
     *
     * @param object 订单内容
     * @throws Exception
     */
    public static CashierResponse cashierPay(CashierRequest request) throws Exception {
        return RemoteInvoker.invoke(request, Config.getCashierPayUrl(), CashierResponse.class);
    }

    /**
     * 支付结果查询
     *
     * @param object 订单内容
     * @throws Exception
     */
    public static PaymentQueryResponse paymentQuery(PaymentQueryRequest request) throws Exception {
        return RemoteInvoker.invoke(request, Config.getPaymentQueryUrl(), PaymentQueryResponse.class);
    }
    //------------------退款----------------

    /**
     * 退款申请
     *
     * @param object 订单内容
     * @throws Exception
     */
    @Deprecated
    public static RefundApplyResponse refundApply(RefundApplyRequest request) throws Exception {
        return RemoteInvoker.invoke(request, Config.getRefundApplyUrl(), RefundApplyResponse.class);
    }

    /**
     * 退款查询
     *
     * @param object 订单内容
     * @throws Exception
     */
    public static RefundApplyQueryResponse refundQuery(RefundApplyQueryRequest request) throws Exception {
        return RemoteInvoker.invoke(request, Config.getRefundQueryUrl(), RefundApplyQueryResponse.class);
    }

    /**
     * 退款
     *
     * @param object 订单内容
     * @throws Exception
     */
    public static RefundResponse refund(RefundRequest request) throws Exception {
        return RemoteInvoker.invoke(request, Config.getRefundUrl(), RefundResponse.class);
    }

    //------------------分账----------------

    /**
     * 分账
     *
     * @param object 订单内容
     * @throws Exception
     */
    public static SplitResponse split(SplitRequest request) throws Exception {
        return RemoteInvoker.invoke(request, Config.getSplitPayUrl(), SplitResponse.class);
    }

    /**
     * 分账查询
     *
     * @param object 订单内容
     * @throws Exception
     */
    public static SplitResultQueryResponse splitResultQuery(SplitResultQueryRequest request) throws Exception {
        return RemoteInvoker.invoke(request, Config.getSplitResultQueryURL(), SplitResultQueryResponse.class);
    }
    //------------------提现----------------

    /**
     * 子商户提现
     *
     * @param object 订单内容
     * @throws Exception
     */
    public static Withdraw4SubMerchantResponse withdraw4SubMerchant(Withdraw4SubMerchantRequest request) throws Exception {
        return RemoteInvoker.invoke(request, Config.getWithdraw4SubMerchantUrl(), Withdraw4SubMerchantResponse.class);
    }

    /**
     * 子商户提现查询
     *
     * @param object 订单内容
     * @throws Exception
     */
    public static WithdrawQuery4SubMerchantResponse withdrawQuery4SubMerchant(WithdrawQuery4SubMerchantRequest request) throws Exception {
        return RemoteInvoker.invoke(request, Config.getWithdrawQuery4SubMerchantUrl(), WithdrawQuery4SubMerchantResponse.class);
    }

    /**
     * 单笔代付
     *
     * @param object 订单内容
     * @throws Exception
     */
    public static WithdrawToCardResponse withdrawalToCard(WithdrawToCardRequest request) throws Exception {
        return RemoteInvoker.invoke(request, Config.getWithdrawalToCardUrl(), WithdrawToCardResponse.class);
    }

    /**
     * 网银支付
     *
     * @param object 订单内容
     * @throws Exception
     */
    public static UnionPayResponse unionPay(UnionPayRequest request) throws Exception {
        return RemoteInvoker.invoke(request, Config.getUnionPayUrl(), UnionPayResponse.class);
    }

    /**
     * 单笔代付查询
     *
     * @param object 订单内容
     * @throws Exception
     */
    public static WithdrawToCardQueryResponse withdrawalToCardQuery(PaymentQueryRequest request) throws Exception {
        return RemoteInvoker.invoke(request, Config.getWithdrawalToCardQueryUrl(), WithdrawToCardQueryResponse.class);
    }

    /**
     * 余额查询
     *
     * @param object 订单内容
     * @throws Exception
     */
    public static CustomerAccountQueryResp accountQuery(Map<String, String> request) throws Exception {
        return RemoteInvoker.invoke(request, Config.getAccountQueryUrl(), CustomerAccountQueryResp.class);
    }

    /**
     * 协议支付绑卡预交易
     *
     * @param object 订单内容
     * @throws Exception
     */
    public static ProtocolPayBindCardResponse bindCard(ProtocolPayBindCardRequest request) throws Exception {
        return RemoteInvoker.invoke(request, Config.getBindCardUrl(), ProtocolPayBindCardResponse.class);
    }

    /**
     * 协议支付绑卡确认
     *
     * @param object 订单内容
     * @throws Exception
     */
    public static ProtocolPayBindCardConfirmResponse bindCardConfirm(ProtocolPayBindCardConfirmRequest request) throws Exception {
        return RemoteInvoker.invoke(request, Config.getBindCardConfirmUrl(), ProtocolPayBindCardConfirmResponse.class);
    }

    /**
     * 协议支付预交易
     *
     * @param object 订单内容
     * @throws Exception
     */
    public static ProtocolPayResponse protocolPayPre(ProtocolPayRequest request) throws Exception {
        return RemoteInvoker.invoke(request, Config.getProtocolPayPreUrl(), ProtocolPayResponse.class);
    }

    /**
     * 协议支付确认交易
     *
     * @param object 订单内容
     * @throws Exception
     */
    public static ProtocolPayConfirmResponse protocolPayConfirm(ProtocolPayConfirmRequest request) throws Exception {
        return RemoteInvoker.invoke(request, Config.getProtocolPayConfirmUrl(), ProtocolPayConfirmResponse.class);
    }

    /**
     * 协议支付解绑
     *
     * @param object 订单内容
     * @throws Exception
     */
    public static ProtocolPayBindCardResponse unBindCard(ProtocolPayUnbindCardRequest request) throws Exception {
        return RemoteInvoker.invoke(request, Config.getUnBindCardUrl(), ProtocolPayBindCardResponse.class);
    }

    /**
     * 协议查询
     *
     * @param object 订单内容
     * @throws Exception
     */
    public static ProtocolQueryResponse protocolQuery(ProtocolQueryRequest request) throws Exception {
        return RemoteInvoker.invoke(request, Config.getProtocolQuery(), ProtocolQueryResponse.class);
    }

    /**
     * 银行卡鉴权
     *
     * @param object 订单内容
     * @throws Exception
     */
    public static AuthenticationResponse authentication(AuthenticationRequest order) throws Exception {
        return RemoteInvoker.invoke(order, Config.getAuthenticationUrl(), AuthenticationResponse.class);
    }


}

