package com.youhe.utils.pay.sdk.pay.domain.protocol;

import com.youhe.utils.pay.sdk.domain.Response;

/**
 * 协议支付返回
 * @author yjw
 *
 */
public class ProtocolPayResponse extends Response{

    /**
     * 客户编码
     */
    private String customerCode;
    /**
     * 会员编号
     */
    private String memberId;
    /**
     * 商户交易订单号
     */
	private String outTradeNo;
	
	/**
	 * 唯一代表该订单
	 */
	private String token;
	
	private String nonceStr;

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
}
