package com.youhe.utils.pay.sdk.pay.domain.protocol;


/**
 * 协议支付绑卡提交
 * @author yjw
 *
 */
public class ProtocolPayConfirmRequest {

	/**
     *订单token
     */
	private String token;
    /**
     * 客户编码
     */
    private String customerCode;
    
	/**
     * 会员编号
     */
    private String memberId;
	/**
	 * 协议号
	 */
	private String protocol;
	
    /**
     * 短息验证码
     */
    private String smsCode;
	
    private String nonceStr;

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
}
