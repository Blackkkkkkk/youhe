package com.youhe.utils.pay.sdk.pay.domain.protocol;

/**
 * 协议支付绑卡提交
 * @author yjw
 *
 */
public class ProtocolPayUnbindCardRequest {

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

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

}
