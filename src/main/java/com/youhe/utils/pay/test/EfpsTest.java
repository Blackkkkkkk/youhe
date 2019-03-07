package com.youhe.utils.pay.test;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


import com.youhe.utils.pay.sdk.pay.domain.cashierPay.OrderInfo;
import org.junit.Before;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;


import com.youhe.utils.pay.sdk.domain.Response;
import com.youhe.utils.pay.sdk.inlet.InletHelper;
import com.youhe.utils.pay.sdk.inlet.domain.Business;
import com.youhe.utils.pay.sdk.inlet.domain.Contact;
import com.youhe.utils.pay.sdk.inlet.domain.InletMerchantQueryRequest;
import com.youhe.utils.pay.sdk.inlet.domain.InletMerchantQueryResponse;
import com.youhe.utils.pay.sdk.inlet.domain.InletMerchantRequest;
import com.youhe.utils.pay.sdk.inlet.domain.InletMerchantResponse;
import com.youhe.utils.pay.sdk.pay.PaymentHelper;
import com.youhe.utils.pay.sdk.pay.domain.WechatH5.WechatH5OrderInfo;
import com.youhe.utils.pay.sdk.pay.domain.WechatH5.WechatH5Request;
import com.youhe.utils.pay.sdk.pay.domain.WechatH5.WechatH5Response;
import com.youhe.utils.pay.sdk.pay.domain.WechatH5.WxH5OrderGoods;
import com.youhe.utils.pay.sdk.pay.domain.WechatH5.WxH5SceneInfo;
import com.youhe.utils.pay.sdk.pay.domain.aliJSAPI.AliJSAPIRequest;
import com.youhe.utils.pay.sdk.pay.domain.aliJSAPI.AliJSAPIResponse;
import com.youhe.utils.pay.sdk.pay.domain.authentication.AuthenticationRequest;
import com.youhe.utils.pay.sdk.pay.domain.authentication.AuthenticationResponse;
import com.youhe.utils.pay.sdk.pay.domain.cashierPay.CashierRequest;
import com.youhe.utils.pay.sdk.pay.domain.cashierPay.CashierResponse;
import com.youhe.utils.pay.sdk.pay.domain.cashierPay.OrderGoods;
import com.youhe.utils.pay.sdk.pay.domain.cashierPay.OrderInfo;
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
import com.youhe.utils.pay.sdk.pay.domain.split.SplitInfo;
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
import com.youhe.utils.pay.sdk.pay.domain.wechatJSAPI.WxJsOrderGoods;
import com.youhe.utils.pay.sdk.pay.domain.wechatJSAPI.WxJsOrderInfo;
import com.youhe.utils.pay.sdk.pay.domain.withdraw.CustomerAccountQueryResp;
import com.youhe.utils.pay.sdk.pay.domain.withdraw.Withdraw4SubMerchantRequest;
import com.youhe.utils.pay.sdk.pay.domain.withdraw.Withdraw4SubMerchantResponse;
import com.youhe.utils.pay.sdk.pay.domain.withdraw.WithdrawQuery4SubMerchantRequest;
import com.youhe.utils.pay.sdk.pay.domain.withdraw.WithdrawQuery4SubMerchantResponse;
import com.youhe.utils.pay.sdk.pay.domain.withdraw.WithdrawToCardQueryResponse;
import com.youhe.utils.pay.sdk.pay.domain.withdraw.WithdrawToCardRequest;
import com.youhe.utils.pay.sdk.pay.domain.withdraw.WithdrawToCardResponse;
import com.youhe.utils.pay.sdk.utils.Config;
import com.youhe.utils.pay.sdk.utils.RsaUtils;
import org.springframework.util.ClassUtils;

public class EfpsTest {

    static final String NOTIFY_URL = "http://172.20.18.116:8080/demo/notify";//异步通知结果地址

    @Before
    public void initialize() throws URISyntaxException {


        Config.initialize(new File(EfpsTest.class.getClassLoader().getResource("config_uat.properties").getPath()));
        // Config.initialize(new File("D:/work/demo/target/test-classes/config_uat.properties"));
        //  Config.initialize(new File("D:\work\demo\target\test-classes\config_test.properties"));

        /*  打印路径地址*/
        System.out.println(EfpsTest.class.getClassLoader().getResource("config_uat.properties").getPath());
        System.out.println(EfpsTest.class.getClassLoader().getResource("").getPath());
        System.out.println(EfpsTest.class.getResource("").getPath());
        System.out.println(this.getClass().getClassLoader().getResource("").getPath());


        System.setProperty("sdk.mode", "debug");
    }

    @Test
    public void mainTest() throws Exception {

        cashierPay();//统一下单接口，不包含分账信息
//        cashierPayWithSplitInfo();//统一下单接口，包含分账信息
//        paymentQuery();//交易结果查询

//        wechatH5Pay();//微信H5支付接口
//        wechatJSAPIPay();//微信JS支付
//        aliJSAPIPay();//支付宝服务窗支付

//        nativePay();//主扫支付
//        microPay();//被扫支付。需要真实付款码
//
//        splitOrder();//分账接口
//        SplitPaymentQuery();//分账结果查询

//        AddMerchant();//新增 子商户
//          ModifyMerchant();//修改 子商户
//        queryMerchant();//子商户新增/修改审核结果查询

        //       refundApply();//退款
//        SplitRefund();//分账退款
//        refundQuery();//退款查询

//        WithdrawalForSubMerchant();//分账子商户提现
//        WithdrawalQueryForSubMerchant();//分账子商户提现结果查询

//    	  withdrawalToCardQuery();//单笔代付查询
//    	  withdrawalToCard();//单笔代付
//    	accountQuery();//余额查询

//        unionPay();//网银支付

        //   	authentication();	//银行卡要素鉴权

//        bindCard();//绑卡预交易
//        bindCardConfirm();//确认绑卡
//        protocolPayPre();//协议支付预交易
//        protocolPayConfirm();//协议支付确认
//        protocolPayUnBindCard();//解除绑卡
//        protocolQuery();//协议查询

    }


    /**
     * 统一下单接口（不包含分账）
     */
    public Response cashierPay() throws Exception {

        String outTradeNo = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()); //交易编号,商户侧唯一
        String customerCode = Config.getCustomerCode(); //商户编号
        String terminalNo = "10001"; //终端号，写死
        String clientIp = "127.0.0.1"; //IP
        long payAmount = 100; //支付金额,分为单位
        String payCurrency = "CNY"; //币种，写死
        String channelType = "01"; //渠道类型，写死

        String notifyUrl = "http://www.baidu.com"; //异步通知地址
        String redirectUrl = "http://www.baidu.com"; //同步通知地址
        String attachData = "attachData"; //备注数据,可空
        String transactionStartTime = Config.getTransactionStartTime();  //交易开始时间
        String transactionEndTime = ""; //交易结束时间

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId("test");
        orderInfo.setBusinessType("test");
        orderInfo.addGood(new OrderGoods("红富士", "1箱", 1));
        orderInfo.addGood(new OrderGoods("82年的茅台", "1瓶", 1));

        CashierRequest order = new CashierRequest(outTradeNo, customerCode, terminalNo, clientIp, orderInfo,
                payAmount, payCurrency, channelType, notifyUrl, redirectUrl,
                attachData, transactionStartTime, transactionEndTime);

//        order.setRechargeMemCustCode("5651300000001052");
        //----分账设置，如需分账，一定要传true
        order.setNeedSplit(false);
        //是否限制信用卡支付，不填默认是false(即不限制)
        order.setNoCreditCards(true);
        CashierResponse response = PaymentHelper.cashierPay(order);
        System.out.println("收银台订购地址：" + response.getCasherUrl());

        return response;
    }

    /**
     * 统一下单接口，包含分账信息
     */
    public Response cashierPayWithSplitInfo() throws Exception {

        String outTradeNo = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()); //交易编号,商户侧唯一
        String customerCode = Config.getCustomerCode(); //商户编号
        String terminalNo = "10001"; //终端号，写死
        String clientIp = "127.0.0.1"; //IP
        long payAmount = 2; //支付金额,分为单位
        String payCurrency = "CNY"; //币种，写死
        String channelType = "01"; //渠道类型，写死

        String notifyUrl = "http://www.baidu.com"; //异步通知地址
        String redirectUrl = "http://www.baidu.com"; //同步通知地址
        String attachData = "attachData"; //备注数据,可空
        String transactionStartTime = Config.getTransactionStartTime(); //交易开始时间
        String transactionEndTime = ""; //交易结束时间

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId("test");
        orderInfo.setBusinessType("test");
        orderInfo.addGood(new OrderGoods("红富士", "1箱", 1));
        orderInfo.addGood(new OrderGoods("82年的茅台", "1瓶", 1));

        CashierRequest order = new CashierRequest(outTradeNo, customerCode, terminalNo, clientIp, orderInfo,
                payAmount, payCurrency, channelType, notifyUrl, redirectUrl,
                attachData, transactionStartTime, transactionEndTime);

        //----分账设置，注意分账金额总和等于交易金额-----
        List<SplitInfo> splitInfoList = new ArrayList<SplitInfo>();
        splitInfoList.add(new SplitInfo(Config.getCustomerCode(), 1, 1));
        splitInfoList.add(new SplitInfo("5651300000000953", 1, 0));

        order.setNeedSplit(true);
        order.setSplitInfoList(splitInfoList);
        order.setSplitNotifyUrl("http://www.baidu.com");//非必填
        order.setSplitAttachData(UUID.randomUUID().toString().replaceAll("-", ""));//非必填
        //----分账设置-----

        //是否限制信用卡支付，不填默认是false(即不限制)
        order.setNoCreditCards(true);
        CashierResponse response = PaymentHelper.cashierPay(order);
        System.out.println("收银台订购地址：" + response.getCasherUrl());

        return response;
    }

    /**
     * 微信H5支付接口
     */
    public Response wechatH5Pay() throws Exception {

        String outTradeNo = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()); //交易编号,商户侧唯一
        String customerCode = Config.getCustomerCode(); //商户编号
        String terminalNo = "10001"; //终端号，写死
        String clientIp = "210.21.43.122"; //IP
        long payAmount = 2000; //支付金额,分为单位
        String payCurrency = "CNY"; //币种，写死
        String channelType = "01"; //渠道类型，写死

        String notifyUrl = "http://www.baidu.com"; //异步通知地址
        String redirectUrl = "http://www.baidu.com"; //同步通知地址
        String attachData = "attachData"; //备注数据,可空
        String transactionStartTime = Config.getTransactionStartTime(); //交易开始时间
        String transactionEndTime = ""; //交易结束时间

        WechatH5OrderInfo orderInfo = new WechatH5OrderInfo();
        orderInfo.setId("test");
        orderInfo.setBusinessType("test");
        orderInfo.addGood(new WxH5OrderGoods("天津肉包子", "一箱", 1));
        orderInfo.addGood(new WxH5OrderGoods("红富士", "1个", 1));

        String type = "Wap";//wap网站URL地址
        String wapURL = "=http://wx.globalcash.cn/new2/txs/cashier";//wap网站URL地址
        String wapName = "收银台";//wap网站URL名称
        WxH5SceneInfo sceneInfo = new WxH5SceneInfo(type, wapURL, wapName);

        WechatH5Request order = new WechatH5Request(outTradeNo, customerCode, terminalNo, clientIp, orderInfo,
                payAmount, payCurrency, channelType, notifyUrl, redirectUrl,
                attachData, transactionStartTime, transactionEndTime, sceneInfo);

        //----分账设置，注意分账金额总和等于交易金额-----
        List<SplitInfo> splitInfoList = new ArrayList<SplitInfo>();
        splitInfoList.add(new SplitInfo(Config.getCustomerCode(), 5000, 1));
        splitInfoList.add(new SplitInfo("5651300000000602", 5000, 0));

        order.setNeedSplit(true);//不填默认否，只有开通了分账业务的商户才可将该值设置为true
        order.setSplitInfoList(splitInfoList);
        order.setSplitNotifyUrl("http://www.baidu.com");//非必填
        order.setSplitAttachData(UUID.randomUUID().toString().replaceAll("-", ""));//非必填
        //----分账设置-----

        //是否限制信用卡支付，不填默认是false(即不限制)
        order.setNoCreditCards(true);
        WechatH5Response response = PaymentHelper.wechatH5Pay(order);
        System.out.println("支付跳转链接：" + response.getMwebURL());

        return response;
    }

    /**
     * 微信JS支付
     */
    public Response wechatJSAPIPay() throws Exception {

        String outTradeNo = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()); //交易编号,商户侧唯一
        String customerCode = Config.getCustomerCode(); //商户编号
        String terminalNo = "10001"; //终端号，写死
        String clientIp = "127.0.0.1"; //IP
        long payAmount = 100; //支付金额,分为单位
        String payCurrency = "CNY"; //币种，写死
        String channelType = "01"; //渠道类型，写死

        String notifyUrl = "http://www.baidu.com"; //异步通知地址
        String attachData = "attachData"; //备注数据,可空
        String transactionStartTime = Config.getTransactionStartTime(); //交易开始时间
        String transactionEndTime = ""; //交易结束时间

        WxJsOrderInfo orderInfo = new WxJsOrderInfo();
        orderInfo.setId("test");
        orderInfo.setBusinessType("test");
        orderInfo.addGood(new WxJsOrderGoods("红富士", "1箱", 1));
        orderInfo.addGood(new WxJsOrderGoods("82年的茅台", "1瓶", 1));

        WechatJSAPIRequest order = new WechatJSAPIRequest(outTradeNo, customerCode, terminalNo, clientIp, orderInfo,
                payAmount, payCurrency, channelType, notifyUrl, attachData,
                transactionStartTime, transactionEndTime);
        order.setAppId("wx92fec07768afb005");
        order.setOpenId("oQD3Xw4ZqJhhyGaT4yeioJaz8zi0");

        //----分账设置，注意分账金额总和等于交易金额-----
        List<SplitInfo> splitInfoList = new ArrayList<SplitInfo>();
        splitInfoList.add(new SplitInfo(Config.getCustomerCode(), 5000, 1));
        splitInfoList.add(new SplitInfo("5651300000000602", 5000, 0));

        order.setNeedSplit(true);//不填默认否，只有开通了分账业务的商户才可将该值设置为true
        order.setSplitInfoList(splitInfoList);
        order.setSplitNotifyUrl("http://www.baidu.com");//非必填
        order.setSplitAttachData(UUID.randomUUID().toString().replaceAll("-", ""));//非必填
        //----分账设置-----

        //是否限制信用卡支付，不填默认是false(即不限制)
        order.setNoCreditCards(true);
        WechatJSAPIResponse response = PaymentHelper.wechatJSAPIPay(order);
        System.out.println("返回结果：" + JSONObject.toJSONString(response));

        return response;
    }

    /**
     * 支付宝服务窗支付
     */
    public Response aliJSAPIPay() throws Exception {

        String outTradeNo = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()); //交易编号,商户侧唯一
        String customerCode = Config.getCustomerCode(); //商户编号
        String terminalNo = "10001"; //终端号，写死
        String clientIp = "127.0.0.1"; //IP
        long payAmount = 1; //支付金额,分为单位
        String payCurrency = "CNY"; //币种，写死
        String channelType = "01"; //渠道类型，写死

        String notifyUrl = "http://www.baidu.com"; //异步通知地址
        String attachData = "attachData"; //备注数据,可空
        String transactionStartTime = Config.getTransactionStartTime(); //交易开始时间
        String transactionEndTime = ""; //交易结束时间

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId("test");
        orderInfo.setBusinessType("test");
        orderInfo.addGood(new OrderGoods("红富士", "1箱", 1));
        orderInfo.addGood(new OrderGoods("82年的茅台", "1瓶", 1));

        /**二选一填写**/
        String buyerId = "2088902560293020";
        String buyerLogonId = "";//支付宝账号

        AliJSAPIRequest order = new AliJSAPIRequest(outTradeNo, customerCode, buyerId, buyerLogonId,
                clientIp, terminalNo, orderInfo, payAmount, payCurrency, channelType, notifyUrl,
                attachData, transactionStartTime, transactionEndTime);

        //----分账设置，注意分账金额总和等于交易金额-----
        List<SplitInfo> splitInfoList = new ArrayList<SplitInfo>();
        splitInfoList.add(new SplitInfo(Config.getCustomerCode(), 5000, 1));
        splitInfoList.add(new SplitInfo("5651300000000602", 5000, 0));

        order.setNeedSplit(true);//不填默认否，只有开通了分账业务的商户才可将该值设置为true
        order.setSplitInfoList(splitInfoList);
        order.setSplitNotifyUrl("http://www.baidu.com");//非必填
        order.setSplitAttachData(UUID.randomUUID().toString().replaceAll("-", ""));//非必填
        //----分账设置-----


        //是否限制信用卡支付，不填默认是false(即不限制)
        order.setNoCreditCards(true);
        AliJSAPIResponse response = PaymentHelper.aliJSAPIPay(order);
        System.out.println("返回结果：" + JSONObject.toJSONString(response));

        return response;
    }

    /**
     * 分账接口
     */
    public Response splitOrder() throws Exception {

        String outTradeNo = "20180517184537526";//new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()); //交易编号,商户侧唯一
        String customerCode = Config.getCustomerCode(); //商户编号
        String notifyUrl = "http://www.baidu.com"; //异步通知地址
        String attachData = "attachData"; //备注数据,可空

        //注意分账金额总和等于被分账交易的交易金额
        List<SplitInfo> splitInfoList = new ArrayList<SplitInfo>();
        splitInfoList.add(new SplitInfo(Config.getCustomerCode(), 5000));
        splitInfoList.add(new SplitInfo("5651300000000604", 5000));

        SplitRequest order = new SplitRequest(outTradeNo, customerCode, splitInfoList, notifyUrl,
                attachData);

        SplitResponse response = PaymentHelper.split(order);
        System.out.println("返回结果：" + JSONObject.toJSONString(response));

        return response;
    }

    /**
     * 分账退款
     */
    public Response SplitRefund() throws Exception {

        String customerCode = Config.getCustomerCode(); //商户编号
        String outRefundNo = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()); //商户退款单号
        //原交易订单号和原商户订单号，选填其中任一项
        String outTradeNo = "132er132e1r03e21213001"; //原支付交易的商户订单号
        String transactionNo = "";//原支付交易的EFPS订单号
        long refundAmount = 300;//申请退款金额,单位分
        long amount = 2000;//原订单支付金额
        String remark = "";//备注
        String notifyUrl = "http://www.baidu.com";//退款结果通知地址

        //原支付订单为需要分账的支付订单时才需要且必须传此字段，要求所有分账金额之和与refundAmount相等
        List<SplitInfo> splitInfoList = new ArrayList<>();
        splitInfoList.add(new SplitInfo("5651300000002202", 100, 1));
        splitInfoList.add(new SplitInfo("5651300000002203", 100, 0));
        splitInfoList.add(new SplitInfo("5651300000002301", 100, 0));

        RefundRequest request = new RefundRequest(
                customerCode, outRefundNo, outTradeNo, transactionNo, refundAmount, amount,
                remark, notifyUrl, splitInfoList);

        RefundResponse response = PaymentHelper.refund(request);
        System.out.println("返回结果：" + JSONObject.toJSONString(response));

        return response;

    }

    /**
     * 分账结果查询
     */
    public Response SplitPaymentQuery() throws Exception {

        //交易订单号和商户订单号，选填其中任一项
        String outTradeNo = "20180517175807044";
        String transactionNo = "";
        String customerCode = Config.getCustomerCode();

        SplitResultQueryRequest request = new SplitResultQueryRequest(
                outTradeNo, transactionNo, customerCode);


        SplitResultQueryResponse response = PaymentHelper.splitResultQuery(request);
        System.out.println("返回结果：" + JSONObject.toJSONString(response));

        return response;
    }

    /**
     * 新增 分账子商户
     */
    public Response AddMerchant() throws Exception {

        //手机号Mobile，营业执照号BusinessLicenseNo，商户名称Name 三者不能重复
        String suffix = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());

        InletMerchantRequest imr = new InletMerchantRequest();
        imr.setName("NNNNNN" + suffix.substring(4));
        imr.setShortName("测试商户简称");
        imr.setMobile("13" + suffix.substring(8));
        imr.setType("50");
        imr.setAreaCode("440000");
        imr.setUseUSCC("1");
        imr.setBusinessLinenseImage("data:image/jpeg;base64,9j/4AAQSkZJRgABAQEAYABgAAD");
        imr.setBusinessLicenseNo(suffix);
        imr.setBusinessLicenesExpiredDate("2099-12-31");
        imr.setLealPersonName("张三" + suffix.substring(2));
        imr.setLealPersonIdentificationType("1");
        imr.setLealPersonIdentificationNo("11" + System.currentTimeMillis());
        imr.setLealPersonIdentificationExpiredDate("2018-12-31");
        imr.setLealPersonIdentificationImage1("data:image/jpg;base64,/9j/4AAQSkZJRgABAQEAYAB");
        imr.setLealPersonIdentificationImage2("data:image/jpg;base64,/9j/4AAQSkZJRgABAQEAYAB");
        imr.setSettCircle("1");
        imr.setBankAccountType("1");
        imr.setBankName("中国银行xx支行");
        imr.setCustomerNameInBank("xxx有限公司");
        imr.setAccountNo("6217003130001660000");
        imr.setBankLineNumber("000000");
        imr.setSettTarget("1");
        imr.setNotifyURL("www.baidu.com");
        imr.setMobile("1" + System.currentTimeMillis());

        List<Business> businessList = new ArrayList<Business>();
        businessList.add(new Business("MEM_INNER_TRANS", "20180504", "20991231", "2", "1"));
        businessList.add(new Business("MEM_REXCHANGE", "20180504", "20991231", "2", "1"));
//        businessList.add(new Business( "WX_NATIVE_PAY",  "20180504",  "20991231",  "2",  "21"));
//        businessList.add(new Business( "ALI_NATIVE_PAY",  "20180504",  "20991231",  "1",  "100"));
        imr.setBusinessList(businessList);

        List<Contact> contactList = new ArrayList<Contact>();
        contactList.add(new Contact(1, imr.getName(), imr.getMobile(), "11@epaylinks.cn", "11111"));
        imr.setContactList(contactList);

        imr.setSettMode("T");

        InletMerchantResponse response = InletHelper.addMerchant(imr);
        System.out.println("返回结果：" + JSONObject.toJSONString(response));

        return response;
    }

    /**
     * 修改 分账子商户
     */
    public Response ModifyMerchant() throws Exception {

        //手机号Mobile，营业执照号BusinessLicenseNo，商户名称Name 三者不能重复，还有LealPersonIdentificationNo
        String suffix = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());

        InletMerchantRequest imr = new InletMerchantRequest();
        imr.setCustomerCode("5651300000000559");//必填
//        imr.setName("NNNNNN" + suffix.substring(4) );
        imr.setShortName("测试商户简称(改");
//        imr.setMobile("13"+ suffix.substring(8));
        imr.setType("50");
        imr.setAreaCode("440000");
        imr.setUseUSCC("1");
        imr.setBusinessLinenseImage("data:image/jpeg;base64,9j/4AAQSkZJRgABAQEAYABgAAD");
//        imr.setBusinessLicenseNo(suffix);
        imr.setBusinessLicenesExpiredDate("2099-12-31");
        imr.setLealPersonName("张三" + suffix.substring(2));
        imr.setLealPersonIdentificationType("1");
//        imr.setLealPersonIdentificationNo("11"+ System.currentTimeMillis());
        imr.setLealPersonIdentificationExpiredDate("2018-12-31");
        imr.setLealPersonIdentificationImage1("data:image/jpg;base64,/9j/4AAQSkZJRgABAQEAYAB");
        imr.setLealPersonIdentificationImage2("data:image/jpg;base64,/9j/4AAQSkZJRgABAQEAYAB");
        imr.setSettCircle("1");
        imr.setBankAccountType("1");
        imr.setBankName("中国银行xx支行");
        imr.setCustomerNameInBank("xxx有限公司");
        imr.setAccountNo("6217003130001660000");
        imr.setBankLineNumber("000000");
        imr.setSettTarget("1");
        imr.setNotifyURL("www.baidu.com");
        imr.setMobile("1" + System.currentTimeMillis());

        List<Business> businessList = new ArrayList<Business>();
        businessList.add(new Business("MEM_INNER_TRANS", "20180504", "20991231", "2", "1"));
        businessList.add(new Business("MEM_REXCHANGE", "20180504", "20991231", "2", "1"));
//        businessList.add(new Business( "WX_NATIVE_PAY",  "20180504",  "20991231",  "2",  "21"));
//        businessList.add(new Business( "ALI_NATIVE_PAY",  "20180504",  "20991231",  "1",  "100"));
        imr.setBusinessList(businessList);

        List<Contact> contactList = new ArrayList<Contact>();
        contactList.add(new Contact(1, imr.getName(), imr.getMobile(), "11@epaylinks.cn", "11111"));
        imr.setContactList(contactList);

        imr.setSettMode("T");

        InletMerchantResponse response = InletHelper.modifyMerchant(imr);
        System.out.println("返回结果：" + JSONObject.toJSONString(response));

        return response;
    }

//    /**
//     * 修改 分账子商户
//     */
//    public Response UpdateSplitMerchant() throws Exception {
//
//        InletMerchantRequest sr = new InletMerchantRequest();
//        sr.setCustomerCode("5651300000000559");//必填，此属性填的是 子商户CustomerCode，在[新增分账子商户]的异步通知结果中获取
//        sr.setName("测试商户名称");
//        sr.setShortName("测试商户简称");
//        sr.setMobile("13800138000");
//        sr.setType("10");
//        sr.setAreaCode("440000");
//        sr.setUseUSCC("1");
//        sr.setBusinessLinenseImage("data:image/jpg;base64,/9j/4AAQSkZJRgABAQEAYAB");
//        sr.setBusinessLicenseNo("123456789");
//        sr.setBusinessLicenesExpiredDate("2099-12-31");
//        sr.setLealPersonName("张三");
//        sr.setLealPersonIdentificationType("1");
//        sr.setLealPersonIdentificationNo("123456789012345679");
//        sr.setLealPersonIdentificationExpiredDate("2018-12-31");
//        sr.setLealPersonIdentificationImage1("data:image/jpg;base64,/9j/4AAQSkZJRgABAQEAYAB");//BASE64
//        sr.setLealPersonIdentificationImage2("data:image/jpg;base64,/9j/4AAQSkZJRgABAQEAYAB");//BASE64
//        sr.setSettCircle("1");
//        sr.setBankAccountType("1");
//        sr.setBankName("中国银行xx支行");
//        sr.setCustomerNameInBank("xxx有限公司");
//        sr.setAccountNo("6222600001112223333");
//        sr.setBankLineNumber("000000");
//        sr.setSettTarget("1");
//        sr.setNotifyURL("www.baidu.com");
//
//        SplitMerchantResponse response = InletMerchantMain.updateSplitMerchantMain( sr , Config.getSplitMerchantUpdateUrl());
//        System.out.println("返回结果："  + JSONObject.toJSONString(response));
//
//        return response;
//    }

    /**
     * 子商户新增/修改审核结果查询
     */
    public Response queryMerchant() throws Exception {
        InletMerchantQueryRequest request = new InletMerchantQueryRequest();
        request.setCustomerInfoId("868");
        request.setNonceStr("13412343");

        InletMerchantQueryResponse response = InletHelper.queryMerchantAuditInfo(request);
        System.out.println("交易结果：" + JSONObject.toJSONString(response));

        return response;
    }

    /**
     * 交易结果查询
     */
    public Response paymentQuery() throws Exception {
        PaymentQueryRequest request = new PaymentQueryRequest();
        request.setCustomerCode(Config.getCustomerCode());//必填
        //交易订单号和商户号订单号，选填其中任一项。如果两个都填，以交易订单号为准
        request.setTransactionNo("");
        request.setOutTradeNo("08b58d70-022c");

        PaymentQueryResponse response = PaymentHelper.paymentQuery(request);
        System.out.println("交易结果：" + JSONObject.toJSONString(response));

        return response;
    }

    /**
     * 主扫支付
     */
    public Response nativePay() throws Exception {

        String outTradeNo = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()); //交易编号,商户侧唯一
        String customerCode = Config.getCustomerCode(); //商户编号
        String terminalNo = "10001"; //终端号，写死
        String clientIp = "127.0.0.1"; //IP
        long payAmount = 20100; //支付金额,分为单位
        String payCurrency = "CNY"; //币种，写死
        String channelType = "01"; //渠道类型，写死

        String notifyUrl = "http://www.baidu.com"; //异步通知地址
        String redirectUrl = "http://www.baidu.com"; //同步通知地址
        String attachData = "attachData"; //备注数据,可空
        String transactionStartTime = Config.getTransactionStartTime(); //交易开始时间
        String transactionEndTime = ""; //交易结束时间
        String payMethod = "24";//6：微信主扫支付  7：支付宝主扫支付 24：银联二维码主扫
        String subCustomerCode = "222222222222222222"; //子商户号：银联主扫需要

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId("test");
        orderInfo.setBusinessType("test");
        orderInfo.addGood(new OrderGoods("红富士", "1箱", 1));
        orderInfo.addGood(new OrderGoods("82年的茅台", "1瓶", 1));

        NativePayRequest order = new NativePayRequest(outTradeNo, customerCode, terminalNo, clientIp,
                orderInfo, payAmount, payCurrency, channelType,
                notifyUrl, redirectUrl, attachData, transactionStartTime,
                transactionEndTime, payMethod, subCustomerCode);

        //----分账设置，注意分账金额总和等于交易金额-----
//        List<SplitInfo> splitInfoList = new ArrayList<SplitInfo>();
//        splitInfoList.add(new SplitInfo(Config.getCustomerCode(),5000, 1));
//        splitInfoList.add(new SplitInfo("5651300000000602",5000, 0));

//        order.setNeedSplit(true);//不填默认否，只有开通了分账业务的商户才可将该值设置为true
//        order.setSplitInfoList(splitInfoList);
        order.setSplitNotifyUrl("http://www.baidu.com");//非必填
        order.setSplitAttachData(UUID.randomUUID().toString().replaceAll("-", ""));//非必填
        //----分账设置-----


        //是否限制信用卡支付，不填默认是false(即不限制)
        order.setNoCreditCards(true);
        NativePayResponse response = PaymentHelper.nativePay(order);
        System.out.println("交易结果：" + JSONObject.toJSONString(response));

        return response;
    }

    /**
     * 被扫支付
     */
    public Response microPay() throws Exception {

        String outTradeNo = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()); //交易编号,商户侧唯一
        String customerCode = Config.getCustomerCode(); //商户编号
        String terminalNo = "10001"; //终端号，写死
        String clientIp = "127.0.0.1"; //IP
        long payAmount = 200100; //支付金额,分为单位
        String payCurrency = "CNY"; //币种，写死
        String channelType = "01"; //渠道类型，写死

        String scene = "bar_code"; //场景信息,写死
        String authCode = "134968773966562513"; //支付条形码，30秒一变
        String attachData = "attachData"; //备注数据,可空
        String transactionStartTime = Config.getTransactionStartTime(); //交易开始时间
        String transactionEndTime = ""; //交易结束时间
        String payMethod = "25";//13：微信被扫支付  14：支付宝被扫支付  25:银联二维码被扫
        String subCustomerCode = "222222222222222222";//子商户号：银联被扫需要

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId("test");
        orderInfo.setBusinessType("test");
        orderInfo.addGood(new OrderGoods("红富士", "1箱", 1));
        orderInfo.addGood(new OrderGoods("82年的茅台", "1瓶", 1));

        MicroPayRequest order = new MicroPayRequest(outTradeNo, customerCode, terminalNo, clientIp,
                orderInfo, payAmount, payCurrency, payMethod, channelType, attachData,
                transactionStartTime, transactionEndTime, authCode,
                scene, subCustomerCode);

        //----分账设置，注意分账金额总和等于交易金额-----
//        List<SplitInfo> splitInfoList = new ArrayList<SplitInfo>();
//        splitInfoList.add(new SplitInfo(Config.getCustomerCode(),5000, 1));
//        splitInfoList.add(new SplitInfo("5651300000000602",5000, 0));
//
//        order.setNeedSplit(true);//不填默认否，只有开通了分账业务的商户才可将该值设置为true
//        order.setSplitInfoList(splitInfoList);
        order.setSplitNotifyUrl("http://www.baidu.com");//非必填
        order.setSplitAttachData(UUID.randomUUID().toString().replaceAll("-", ""));//非必填
        //----分账设置-----

        //是否限制信用卡支付，不填默认是false(即不限制)
        order.setNoCreditCards(true);
        MicroPayResponse response = PaymentHelper.microPay(order);
        System.out.println("交易结果：" + JSONObject.toJSONString(response));

        return response;
    }

    /**
     * 退款
     *
     * @return
     * @throws Exception
     */
    public Response refundApply() throws Exception {

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

        RefundApplyRequest order = new RefundApplyRequest(transactionNo, outTradeNo, customerCode, amount,
                notifyUrl, sourceChannel, outRefundNo, refundAmount,
                remark, userId);

        RefundApplyResponse response = PaymentHelper.refundApply(order);
        System.out.println("交易结果：" + JSONObject.toJSONString(response));

        return response;
    }

    /**
     * 退款查询
     *
     * @return
     * @throws Exception
     */
    public Response refundQuery() throws Exception {

        String customerCode = "5651300000000151";//商户编号
        String outRefundNo = "20180525171018148";//商户退款单号

        RefundApplyQueryRequest order = new RefundApplyQueryRequest(customerCode, outRefundNo);

        RefundApplyQueryResponse response = PaymentHelper.refundQuery(order);
        System.out.println("交易结果：" + JSONObject.toJSONString(response));

        return response;
    }

    /**
     * 分账子商户提现
     *
     * @return
     * @throws Exception
     */
    public Response WithdrawalForSubMerchant() throws Exception {

        String outTradeNo = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()); //交易编号,商户侧唯一
//        String customerCode = "";
        String subCustomerCode = "5651300000000698";
        long payAmount = 10;
        String payCurrency = "CNY";
        String notifyUrl = NOTIFY_URL + "/WithdrawalForSubMerchant";
        String remark = "";

        Withdraw4SubMerchantRequest request = new Withdraw4SubMerchantRequest(outTradeNo, subCustomerCode, payAmount,
                payCurrency, notifyUrl, remark);


        Withdraw4SubMerchantResponse response = PaymentHelper.withdraw4SubMerchant(request);
        System.out.println("交易结果：" + JSONObject.toJSONString(response));

        return response;
    }

    /**
     * 分账子商户提现结果查询
     *
     * @return
     * @throws Exception
     */
    public Response WithdrawalQueryForSubMerchant() throws Exception {

        WithdrawQuery4SubMerchantRequest request = new WithdrawQuery4SubMerchantRequest();
        request.setCustomerCode(Config.getCustomerCode());//必填
        //交易订单号和商户号订单号，选填其中任一项。如果两个都填，以交易订单号为准
        request.setTransactionNo("");
        request.setOutTradeNo("02");

        WithdrawQuery4SubMerchantResponse response = PaymentHelper.withdrawQuery4SubMerchant(request);

        System.out.println("交易结果：" + JSONObject.toJSONString(response));

        return response;
    }

    /**
     * 网银支付
     *
     * @return
     * @throws Exception
     */
    public Response unionPay() throws Exception {
        String outTradeNo = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()); //交易编号,商户侧唯一
        String customerCode = Config.getCustomerCode(); //商户编号
        String terminalNo = "10001"; //终端号，写死
        String clientIp = "127.0.0.1"; //IP
        long payAmount = 200; //支付金额,分为单位
        String payCurrency = "CNY"; //币种，写死
        String channelType = "01"; //渠道类型，写死

        String notifyUrl = "http://www.baidu.com"; //异步通知地址
        String attachData = "attachData"; //备注数据,可空
        String transactionStartTime = Config.getTransactionStartTime(); //交易开始时间
        String transactionEndTime = ""; //交易结束时间
        String redirectUrl = "http://www.baidu.com"; //同步通知地址
        String frontFailUrl = "http://www.baidu.com"; //同步通知失败地址
        String issInsCode = "CMBD";//银行编码
        String bankCardType = "debit";//（debit：借记卡，credit：贷记卡）

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId("test");
        orderInfo.setBusinessType("test");
        orderInfo.addGood(new OrderGoods("红富士", "1箱", 1));
        orderInfo.addGood(new OrderGoods("82年的茅台", "1瓶", 1));

        UnionPayRequest request = new UnionPayRequest();
        request.setOutTradeNo(outTradeNo);
        request.setCustomerCode(customerCode);
        request.setTerminalNo(terminalNo);
        request.setClientIp(clientIp);
        request.setOrderInfo(orderInfo);
        request.setPayAmount(payAmount);
        request.setPayCurrency(payCurrency);
        request.setChannelType(channelType);
        request.setNotifyUrl(notifyUrl);
        request.setAttachData(attachData);
        request.setTransactionStartTime(transactionStartTime);
        request.setTransactionEndTime(transactionEndTime);
        request.setNonceStr(UUID.randomUUID().toString().replaceAll("-", ""));
        request.setFrontUrl(redirectUrl);
        request.setFrontFailUrl(frontFailUrl);
        request.setIssInsCode(issInsCode);
        request.setBankCardType(bankCardType);
        request.setTradeType("union_online");//union_online：银联在线  personal_bank: 网银支付

        UnionPayResponse response = PaymentHelper.unionPay(request);
        System.out.println("交易结果：" + JSONObject.toJSONString(response));

        return response;
    }

    /**
     * 单笔代付
     *
     * @return
     * @throws Exception
     */
    public Response withdrawalToCard() throws Exception {

        String outTradeNo = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()); //交易编号,商户侧唯一
//        String customerCode = "";
        long payAmount = 10;
        String payCurrency = "CNY";
        String notifyUrl = NOTIFY_URL + "/WithdrawalForSubMerchant";
        String remark = "";

        String publicKeyPath = Config.getPublicKeyFile().getAbsolutePath();

        String bankUserName = RsaUtils.encryptByPublicKey("张三", RsaUtils.getPublicKey(publicKeyPath));
        String bankUserCert = RsaUtils.encryptByPublicKey("3623xxxx234324", RsaUtils.getPublicKey(publicKeyPath));
        String bankCardNo = RsaUtils.encryptByPublicKey("62451804xxxxxxxx", RsaUtils.getPublicKey(publicKeyPath));

        WithdrawToCardRequest request = new WithdrawToCardRequest();
        request.setOutTradeNo(outTradeNo);
        request.setCustomerCode(Config.getCustomerCode());
        request.setAmount(payAmount);
        request.setIsAdvanceFund("");

        request.setBankUserName(bankUserName);
        request.setBankUserCert(bankUserCert);
        request.setBankCardNo(bankCardNo);
//        request.setBankNo("102xxx0012");//联行号
        request.setBankName("招商银行");
        request.setBankProvince("广东省");
        request.setBankCity("广州市");
        request.setBankSub("xxxxx支行");
        request.setBankAccountType("2");//1：对公，2：对私
        request.setPayCurrency(payCurrency);
        request.setNotifyUrl(notifyUrl);
        request.setRemark(remark);
        request.setNonceStr(UUID.randomUUID().toString().replaceAll("-", ""));


        WithdrawToCardResponse response = PaymentHelper.withdrawalToCard(request);
        System.out.println("交易结果：" + JSONObject.toJSONString(response));

        return response;
    }

    /**
     * 单笔代付结果查询
     *
     * @return
     * @throws Exception
     */
    public Response withdrawalToCardQuery() throws Exception {

        PaymentQueryRequest request = new PaymentQueryRequest();
        request.setCustomerCode(Config.getCustomerCode());//必填
        //交易订单号和商户号订单号，选填其中任一项。如果两个都填，以交易订单号为准
        request.setTransactionNo("TX201809171119463009002");
        request.setOutTradeNo("");
        request.setNonceStr(UUID.randomUUID().toString().replaceAll("-", ""));
        WithdrawToCardQueryResponse response = PaymentHelper.withdrawalToCardQuery(request);

        System.out.println("交易结果：" + JSONObject.toJSONString(response));

        return response;
    }

    /**
     * 余额查询
     *
     * @return
     * @throws Exception
     */
    public Response accountQuery() throws Exception {

        Map<String, String> request = new HashMap<String, String>();
        request.put("customerCode", Config.getCustomerCode());
        request.put("accountType", "2");//固定传2-交易账户
        request.put("nonceStr", UUID.randomUUID().toString().replaceAll("-", ""));

        CustomerAccountQueryResp response = PaymentHelper.accountQuery(request);

        System.out.println("交易结果：" + JSONObject.toJSONString(response));

        return response;
    }


    /**
     * 银行卡要素鉴权
     */
    public Response authentication() throws Exception {

        String outTradeNo = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()); //交易编号,商户侧唯一
        String customerCode = Config.getCustomerCode(); //商户编号

        String publicKeyPath = Config.getPublicKeyFile().getAbsolutePath();

        String certNameEnc = RsaUtils.encryptByPublicKey("柯工要", RsaUtils.getPublicKey(publicKeyPath));
        String certIdEnc = RsaUtils.encryptByPublicKey("445302198805273030", RsaUtils.getPublicKey(publicKeyPath));
        String cardNoEnc = RsaUtils.encryptByPublicKey("6214830202152288", RsaUtils.getPublicKey(publicKeyPath));
        String telephoneNoEnc = RsaUtils.encryptByPublicKey("13430323919", RsaUtils.getPublicKey(publicKeyPath));
        String cvnEnc = RsaUtils.encryptByPublicKey("1234", RsaUtils.getPublicKey(publicKeyPath));
        String expiredDateEnc = RsaUtils.encryptByPublicKey("0222", RsaUtils.getPublicKey(publicKeyPath));

        String telephoneNo = "13957212294"; //手机号
        String certId = "420101198101018485"; //持卡人身份证号
        String certName = "金宜豪"; //持卡人姓名
        String cardNo = "6222027776755184218 "; //异步通知地址

//        String cvn = "4312"; //渠道类型，写死
//        String expiredDate = "0222"; //有效期,22年2月


        AuthenticationRequest order = new AuthenticationRequest(outTradeNo, customerCode, telephoneNoEnc, certIdEnc,
                certNameEnc, null, cardNoEnc, expiredDateEnc);

        System.out.println("=================\n");
        System.out.println(order.toString());
        AuthenticationResponse response = PaymentHelper.authentication(order);
        System.out.println("交易结果：" + JSONObject.toJSONString(response));

        return response;
    }

    /**
     * 协议支付绑卡预交易
     *
     * @return
     * @throws Exception
     */
    public Response bindCard() throws Exception {

        String outTradeNo = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()); // 交易编号,商户侧唯一
//        String customerCode = "";
        long payAmount = 10;
        String payCurrency = "CNY";
        String notifyUrl = NOTIFY_URL + "/WithdrawalForSubMerchant";
        String remark = "";

        String publicKeyPath = Config.getPublicKeyFile().getAbsolutePath();

//		String userName = RsaUtils.encryptByPublicKey("银联一", RsaUtils.getPublicKey(publicKeyPath));
//		String certificatesNo = RsaUtils.encryptByPublicKey("310115198903261113", RsaUtils.getPublicKey(publicKeyPath));
//		String bankCardNo = RsaUtils.encryptByPublicKey("6212143000000000011", RsaUtils.getPublicKey(publicKeyPath));
//		String phoneNum = RsaUtils.encryptByPublicKey("13111111111", RsaUtils.getPublicKey(publicKeyPath));
        String cvn = RsaUtils.encryptByPublicKey("672", RsaUtils.getPublicKey(publicKeyPath));
        // 2018年2月
        String expired = RsaUtils.encryptByPublicKey("2209", RsaUtils.getPublicKey(publicKeyPath));

        String userName = RsaUtils.encryptByPublicKey("com", RsaUtils.getPublicKey(publicKeyPath));
        String certificatesNo = RsaUtils.encryptByPublicKey("310115197803261111", RsaUtils.getPublicKey(publicKeyPath));
        String bankCardNo = RsaUtils.encryptByPublicKey("6224243000000011", RsaUtils.getPublicKey(publicKeyPath));
        String phoneNum = RsaUtils.encryptByPublicKey("13222222222", RsaUtils.getPublicKey(publicKeyPath));

        ProtocolPayBindCardRequest request = new ProtocolPayBindCardRequest();

        request.setCustomerCode(Config.getCustomerCode());
        request.setMemberId("mb201811300002");// 会员号
        request.setMchtOrderNo(outTradeNo);
        request.setPhoneNum(phoneNum);// 手机号
        request.setUserName(userName);// 持卡人姓名
        request.setBankCardNo(bankCardNo);// 银行卡
        request.setBankCardType("debit");// debit:借记卡,credit:贷记卡;
        request.setCvn(cvn);// cvn 卡背后三位数 信用卡必填
        request.setExpired(expired);// 卡有效期 信用卡必填 yymm
        request.setCertificatesNo(certificatesNo);// 身份证号
        request.setCertificatesType("01");// 固定传01
        request.setNonceStr(UUID.randomUUID().toString().replaceAll("-", ""));

        ProtocolPayBindCardResponse response = PaymentHelper.bindCard(request);
        System.out.println("交易结果：" + JSONObject.toJSONString(response));
        System.out.println("SmsNo：" + response.getSmsNo());

        return response;
    }

    /**
     * 协议支付绑卡确认
     *
     * @return
     * @throws Exception
     */
    public Response bindCardConfirm() throws Exception {

        ProtocolPayBindCardConfirmRequest request = new ProtocolPayBindCardConfirmRequest();

        request.setCustomerCode(Config.getCustomerCode());
        request.setMemberId("mb201811300002");// 会员号
        request.setSmsCode("111111");// 验证码
        request.setSmsNo("QY201812031420243019075");// 绑卡流水号 绑卡预交易返回
        request.setNonceStr(UUID.randomUUID().toString().replaceAll("-", ""));

        ProtocolPayBindCardConfirmResponse response = PaymentHelper.bindCardConfirm(request);
        System.out.println("交易结果：" + JSONObject.toJSONString(response));

        return response;
    }

    /**
     * 协议支付绑卡确认
     *
     * @return
     * @throws Exception
     */
    public Response protocolPayPre() throws Exception {

        String outTradeNo = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()); // 交易编号,商户侧唯一
        String clientIp = "127.0.0.1"; // IP
        long payAmount = 20; // 支付金额,分为单位
        String payCurrency = "CNY"; // 币种，写死

        String attachData = "attachData"; // 备注数据,可空
        String transactionStartTime = Config.getTransactionStartTime(); // 交易开始时间
        String transactionEndTime = ""; // 交易结束时间

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId("test");
        orderInfo.setBusinessType("test");
        orderInfo.addGood(new OrderGoods("红富士", "1箱", 1));
        orderInfo.addGood(new OrderGoods("82年的茅台", "1瓶", 1));

        ProtocolPayRequest request = new ProtocolPayRequest();
        request.setCustomerCode(Config.getCustomerCode());
        request.setMemberId("mb201811300002");// 会员号
        request.setPayAmount(payAmount);
        request.setOutTradeNo(outTradeNo);
        request.setPayCurrency(payCurrency);
        request.setProtocol("");// 协议号
        request.setAttachData(attachData);
        request.setNeedSplit(false);
        request.setOrderInfo(orderInfo);
        request.setSplitNotifyUrl("");
        request.setTransactionStartTime(transactionStartTime);
        request.setTransactionEndTime(transactionEndTime);
        request.setNotifyUrl("http://www.baidu.com");// 异步通知
        request.setNonceStr(UUID.randomUUID().toString().replaceAll("-", ""));

        ProtocolPayResponse response = PaymentHelper.protocolPayPre(request);
        System.out.println("交易结果：" + JSONObject.toJSONString(response));
        System.out.println("token：" + response.getToken());

        return response;
    }

    /**
     * 协议支付绑卡确认
     *
     * @return
     * @throws Exception
     */
    public Response protocolPayConfirm() throws Exception {

        ProtocolPayConfirmRequest request = new ProtocolPayConfirmRequest();

        request.setCustomerCode(Config.getCustomerCode());
        request.setMemberId("mb201811300002");// 会员号
        request.setSmsCode("xxx");// 验证码
        request.setToken("");// token
        request.setProtocol("");// 协议号
        request.setNonceStr(UUID.randomUUID().toString().replaceAll("-", ""));

        ProtocolPayConfirmResponse response = PaymentHelper.protocolPayConfirm(request);
        System.out.println("交易结果：" + JSONObject.toJSONString(response));

        return response;
    }

    /**
     * 协议支付解除绑定
     *
     * @return
     * @throws Exception
     */
    public Response protocolPayUnBindCard() throws Exception {

        ProtocolPayUnbindCardRequest request = new ProtocolPayUnbindCardRequest();

        request.setCustomerCode(Config.getCustomerCode());
        request.setMemberId("mb201811300002");// 会员号
        request.setProtocol("");// 协议号
        request.setNonceStr(UUID.randomUUID().toString().replaceAll("-", ""));

        ProtocolPayBindCardResponse response = PaymentHelper.unBindCard(request);
        System.out.println("交易结果：" + JSONObject.toJSONString(response));

        return response;
    }

    /**
     * 协议查询
     *
     * @return
     * @throws Exception
     */
    public Response protocolQuery() throws Exception {

        ProtocolQueryRequest request = new ProtocolQueryRequest();

        request.setCustomerCode(Config.getCustomerCode());
        request.setMemberId("mb201811300002");// 会员号
//		request.setProtocol("");// 协议号
        request.setNonceStr(UUID.randomUUID().toString().replaceAll("-", ""));

        ProtocolQueryResponse response = PaymentHelper.protocolQuery(request);
        System.out.println("交易结果：" + JSONObject.toJSONString(response));

        return response;
    }

}
