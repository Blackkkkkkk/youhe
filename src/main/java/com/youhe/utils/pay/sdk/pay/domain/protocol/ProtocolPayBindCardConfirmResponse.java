package com.youhe.utils.pay.sdk.pay.domain.protocol;

import com.youhe.utils.pay.sdk.domain.Response;

/**
 * 协议支付绑卡提交返回
 * @author yjw
 *
 */
public class ProtocolPayBindCardConfirmResponse extends Response{

    /**
     * 客户编码
     */
    private String customerCode;
    /**
     * 流水号
     */
    private String smsNo;
	
    /**
     * 会员编号
     */
    private String memberId;
    
    /**
     * 协议号
     */
    private String protocol;
    
    
	public String getCustomerCode() {
		return customerCode;
	}


	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}


	public String getSmsNo() {
		return smsNo;
	}


	public void setSmsNo(String smsNo) {
		this.smsNo = smsNo;
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

	@Override
	public String toString() {
		return "ProtocolPayBindCardConfirmResponse [customerCode=" + customerCode + ", smsNo=" + smsNo + ", memberId="
				+ memberId + ", protocol=" + protocol + "]";
	}
}
