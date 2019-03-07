package com.youhe.utils.pay.sdk.pay.domain.protocol;

public class ProtocolInfo {
	
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
	 * 手机号码
	 */
    private String phoneNum;
    
    /**
     * 协议状态 "Invalid" , "无效", "Valid" , "有效"
     */
    private String state;

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
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

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
