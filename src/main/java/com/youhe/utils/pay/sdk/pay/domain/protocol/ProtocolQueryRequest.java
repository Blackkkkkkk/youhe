package com.youhe.utils.pay.sdk.pay.domain.protocol;


/**
 * 协议查询请求
 * @author yjw
 *
 */

public class ProtocolQueryRequest{

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
	 * 银行卡号
	 */
    private String bankCardNo;
    
    /**
     * 银行卡类型 "debit", "借记卡" "credit", "贷记卡"
     */
    private String bankCardType;
    
    /**
     * 协议状态 "Invalid" , "无效", "Valid" , "有效"
     */
    private String state;
    
    private String nonceStr;
    
    
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getBankCardNo() {
		return bankCardNo;
	}
	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}
	public String getBankCardType() {
		return bankCardType;
	}
	public void setBankCardType(String bankCardType) {
		this.bankCardType = bankCardType;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	@Override
	public String toString() {
		return "ProtocolQueryRequest [customerCode=" + customerCode + ", memberId=" + memberId + ", bankCardNo="
				+ bankCardNo + ", bankCardType=" + bankCardType + ", state=" + state + ", protocol=" + protocol
				+ ", nonceStr=" + nonceStr + "]";
	}
}
