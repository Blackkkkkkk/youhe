package com.youhe.utils.pay.test;

import java.io.File;
import java.net.URISyntaxException;
import java.util.UUID;

import com.youhe.utils.pay.sdk.member.MemberHelper;
import org.junit.Before;
import org.junit.Test;

import com.alibaba.fastjson.JSONObject;

import com.youhe.utils.pay.sdk.member.MemberHelper;
import com.youhe.utils.pay.sdk.member.domain.balanceQuery.BalanceQueryRequest;
import com.youhe.utils.pay.sdk.member.domain.balanceQuery.BalanceQueryResponse;
import com.youhe.utils.pay.sdk.member.domain.transfer.TransferRequest;
import com.youhe.utils.pay.sdk.member.domain.transfer.TransferResponse;
import com.youhe.utils.pay.sdk.member.domain.transferQuery.TransferQueryRequest;
import com.youhe.utils.pay.sdk.member.domain.transferQuery.TransferQueryResponse;
import com.youhe.utils.pay.sdk.member.domain.withdraw.WithdrawRequest;
import com.youhe.utils.pay.sdk.member.domain.withdraw.WithdrawResponse;
import com.youhe.utils.pay.sdk.member.domain.withdrawQuery.WithdrawQueryRequest;
import com.youhe.utils.pay.sdk.member.domain.withdrawQuery.WithdrawQueryResponse;
import com.youhe.utils.pay.sdk.utils.Config;

public class MemberTester {
    @Before
    public void initialize() throws URISyntaxException {
		com.youhe.utils.pay.sdk.utils.Config.initialize(new File(ClassLoader.getSystemResource("config_uat.properties").toURI()));
        System.setProperty("sdk.mode", "debug");
    }

    @Test
    public void mainTest()  throws Exception{
//    	transfer();
//    	withdraw();
		withdrawQuery();
    }

    public void transfer() throws Exception {
    	TransferRequest request = new TransferRequest();
//    	request.setSrcCustomerCode(Config.getCustomerCode());
//    	request.setTargetCustomerCode("5651300000001052");
    	request.setTargetCustomerCode(Config.getCustomerCode());
    	request.setSrcCustomerCode("5651300000001052");
    	request.setOutTradeNo("TR" + System.currentTimeMillis());
    	request.setAmount(1000L);
    	request.setNonceStr(UUID.randomUUID().toString().replaceAll("-", ""));
    	request.addSharedInfo("5651300000001060", 2L);
    	TransferResponse response = MemberHelper.transfer(request);
    	System.out.println(JSONObject.toJSONString(response));
    }

    public void transferQuery() throws Exception {
    	TransferQueryRequest request = new TransferQueryRequest();
    	request.setOutTradeNo("TR" + "22222222");
    	TransferQueryResponse response = MemberHelper.transferQuery(request);
    	System.out.println(JSONObject.toJSONString(response));
    }

    public void balanceQuery() throws Exception {
    	BalanceQueryRequest request = new BalanceQueryRequest();
    	request.setCustomerCode(Config.getCustomerCode());
    	request.setMemberCustomerCode("5651300000001052");
    	request.setNonceStr(UUID.randomUUID().toString().replaceAll("-", ""));
    	BalanceQueryResponse response = MemberHelper.balanceQuery(request);
    	System.out.println(JSONObject.toJSONString(response));
    }

    public void withdraw() throws Exception {
    	WithdrawRequest request = new WithdrawRequest();
    	request.setCustomerCode(Config.getCustomerCode());
    	request.setMemberCustomerCode("5651300000000151");
    	request.setAmount(200L);
    	request.setOutTradeNo("TX" + System.currentTimeMillis());
    	request.setNotifyUrl("https://www.baidu.com");
    	request.setNonceStr(UUID.randomUUID().toString().replaceAll("-", ""));
    	WithdrawResponse response = MemberHelper.withdraw(request);
    	System.out.println(JSONObject.toJSONString(response));
    }

    public void withdrawQuery() throws Exception {
    	long start = System.currentTimeMillis();
    	WithdrawQueryRequest request = new WithdrawQueryRequest();
    	request.setCustomerCode(Config.getCustomerCode());
    	request.setMemberCustomerCode("5651300000000151");
    	request.setOutTradeNo("TX1531190814314");
    	request.setNonceStr(UUID.randomUUID().toString().replaceAll("-", ""));
    	WithdrawQueryResponse response = MemberHelper.withdrawQuery(request);
    	System.out.println(JSONObject.toJSONString(response));
    	System.out.println("--------------------->:" + (System.currentTimeMillis() - start));
    }
}
