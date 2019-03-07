package com.youhe.utils.pay.sdk.utils;

import com.youhe.controller.user.shop.index.IndexController;

import java.io.File;
import java.io.FileInputStream;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public abstract class Config {

    private static final Properties props = new Properties();

    private static boolean initialized = false;

    public static void initialize(File file) {
        System.out.println(file.getAbsoluteFile());
        if (initialized) {
            return;
        }
        synchronized (Config.class) {
            if (initialized) {
                return;
            }
            try {
                props.load(new FileInputStream(file));
            } catch (Exception e) {
                e.printStackTrace();
            }
            initialized = true;
        }
    }

    public static String getProperty(String key) {
        if (!initialized) {
            try {
                initialize(new File(ClassLoader.getSystemResource("config.properties").toURI()));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        return props.getProperty(key);
    }

    // -------------统一下单配置---------------------
    public static String getCashierPayUrl() {
        return getProperty("cashier_pay_url");
    }

    // -------------WxH5支付配置---------------------
    public static final String getWechatH5Url() {
        return getProperty("wxh5_url");
    }

    // -------------微信JS支付---------------------
    public static final String getWechatJSAPIUrl() {
        return getProperty("wxJSAPI_url");
    }

    // -------------支付宝服务窗支付---------------------
    public static final String getAliJSAPIUrl() {
        return getProperty("aliJSAPI_url");
    }

    // -------------分账子商户 新增---------------------
    public static final String getAddMerchantUrl() {
        return getProperty("inlet_add_merchant");
    }

    // -------------分账子商户 修改---------------------
    public static final String getModifyMerchantUrl() {
        return getProperty("inlet_modify_merchant");
    }

    // -------------分账子商户新增/修改审核结果查询---------------------
    public static final String getInletMerchantQueryURL() {
        return getProperty("inlet_query_merchant");
    }

    // -------------支付结果查询---------------------
    public static final String getPaymentQueryUrl() {
        return getProperty("paymentQuery_url");
    }

    // -------------分账接口---------------------
    public static final String getSplitPayUrl() {
        return getProperty("splitPay_url");
    }

    // -------------分账结果查询---------------------
    public static final String getSplitResultQueryURL() {
        return getProperty("splitResultQuery_url");
    }

    // -------------主扫支付---------------------
    public static final String getNativePayUrl() {
        return getProperty("mainScaning_url");
    }

    // -------------被扫支付---------------------
    public static final String getScaningURL() {
        return getProperty("scaning_url");
    }

    // -------------退款---------------------
    public static final String getRefundApplyUrl() {
        return getProperty("refund_apply_url");
    }

    // -------------分账退款---------------------
    public static final String getRefundUrl() {
        return getProperty("refund_apply_url");
    }

    // -------------退款结果查询---------------------
    public static final String getRefundQueryUrl() {
        return getProperty("refund_query_url");
    }

    // -------------分账子商户提现---------------------
    public static final String getWithdraw4SubMerchantUrl() {
        return getProperty("withdraw_sub_merchant_url");
    }

    // -------------分账子商户提现结果查询---------------------
    public static final String getWithdrawQuery4SubMerchantUrl() {
        return getProperty("withdraw_query_sub_merchant_url");
    }

    // -------------银联支付---------------------
    public static final String getUnionPayUrl() {
        return getProperty("unionPay");
    }

    // -------------单笔代付---------------------
    public static final String getWithdrawalToCardUrl() {
        return getProperty("withdrawalToCard");
    }

    // -------------代付结果查询---------------------
    public static final String getWithdrawalToCardQueryUrl() {
        return getProperty("withdrawalToCardQuery");
    }

    // -------------余额查询---------------------
    public static final String getAccountQueryUrl() {
        return getProperty("accountQuery");
    }

    // -------------协议支付绑卡预交易---------------------
    public static final String getBindCardUrl() {
        return getProperty("bindCard");
    }

    // -------------协议支付绑卡确认---------------------
    public static final String getBindCardConfirmUrl() {
        return getProperty("bindCardConfirm");
    }

    // -------------协议支付预交易---------------------
    public static final String getProtocolPayPreUrl() {
        return getProperty("protocolPayPre");
    }

    // -------------协议支付确认交易---------------------
    public static final String getProtocolPayConfirmUrl() {
        return getProperty("protocolPayConfirm");
    }

    // -------------协议支付确认交易---------------------
    public static final String getUnBindCardUrl() {
        return getProperty("protocolPayUnBindCard");
    }

    // -------------协议查询---------------------
    public static final String getProtocolQuery() {
        return getProperty("protocolQuery");
    }


    // -------------会员内转---------------------
    public static final String getMemberTransferUrl() {
        return getProperty("member_transfer_url");
    }

    // -------------会员内转結果查詢---------------------
    public static final String getMemberTransferQueryUrl() {
        return getProperty("member_transfer_query_url");
    }

    // -------------余额查询---------------------
    public static final String getMemberBalanceQueryUrl() {
        return getProperty("member_balance_query_url");
    }

    // -------------提现---------------------
    public static final String getMemberWithdrawUrl() {
        return getProperty("member_withdraw_url");
    }

    // -------------提现查询---------------------
    public static final String getMemberWithdrawQueryUrl() {
        return getProperty("member_withdraw_query_url");
    }

    /**
     * @return 公钥文件
     * @throws Exception
     */
    public static File getPublicKeyFile() throws Exception {
        String key = getProperty("public_key");
        File keyFile = new File(key);
        if (keyFile.exists()) {
            return keyFile;
        } else {
            return new File(Config.class.getClassLoader().getResource("").getPath() + "static/payproperties/" + key);

            //  return new File(ClassLoader.getSystemResource(key).toURI());
        }
    }

    /**
     * @return 私钥文件
     * @throws Exception
     */
    public static File getPrivateKeyFile() throws Exception {
        String key = getProperty("private_key");
        File keyFile = new File(key);
        if (keyFile.exists()) {
            return keyFile;
        } else {

            return new File(Config.class.getClassLoader().getResource("").getPath() + "static/payproperties/" + key);
        }
    }

    /**
     * @return 私钥文件保护密码
     */
    public static String getPrivateKeyPassword() {
        return getProperty("private_key_pwd");
    }

    public static String getSignNo() {
        return getProperty("sign_no");
    }

    /**
     * @return 易票联商户号
     */
    public static String getCustomerCode() {
        return getProperty("customer_code");
    }

    public static String getTransactionStartTime() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

    // -------------银行卡要素鉴权---------------------
    public static final String getAuthenticationUrl() {
        return getProperty("authenticationUrl");
    }
}
