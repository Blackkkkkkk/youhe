package com.youhe.utils.pay.sdk.pay.domain.protocol;


/**
 * 协议支付绑卡提交
 * @author yjw
 *
 */
public class ProtocolPayBindCardConfirmRequest {

	/**
     * 绑卡流水号
     */
    private String smsNo;
    /**
     * 客户编码
     */
    private String customerCode;
    
    private String memberId;
    /**
     * 短息验证码
     */
    private String smsCode;
	
    private String nonceStr;

	public String getSmsNo() {
		return smsNo;
	}

	public void setSmsNo(String smsNo) {
		this.smsNo = smsNo;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
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

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	@Override
	public String toString() {
		return "ProtocolPayBindCardConfirmRequest [smsNo=" + smsNo + ", customerCode=" + customerCode + ", memberId="
				+ memberId + ", smsCode=" + smsCode + ", nonceStr=" + nonceStr + "]";
	}
}
