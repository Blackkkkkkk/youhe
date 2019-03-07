package com.youhe.utils.pay.sdk.pay.domain.protocol;


/**
 * 协议支付绑卡请求
 * @author yjw
 *
 */

public class ProtocolPayBindCardRequest{

	/**
	 * 请求流水号
	 */
	private String mchtOrderNo;
    /**
     * 客户编码
     */
    private String customerCode;
    /**
     * 会员编号
     */
    private String memberId;
    
    /**
	 * 会员姓名
	 */
    private String userName;
    
    /**
	 * 手机号码
	 */
    private String phoneNum;
    
    /**
	 * 银行卡号
	 */
    private String bankCardNo;
    
    /**
     * 银行卡类型 "debit", "借记卡" "credit", "贷记卡"
     */
    private String bankCardType;
    
    /**
	 * 证件类型 身份证00
	 */
    private String certificatesType;
    /**
	 * 证件号码
	 */
    private String certificatesNo;
    
    /**
	 * 有效日期(信用卡)
	 */
    private String expired;
    /**
	 * cvn（信用卡）
	 */
    private String cvn;
    
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
	public String getCertificatesType() {
		return certificatesType;
	}
	public void setCertificatesType(String certificatesType) {
		this.certificatesType = certificatesType;
	}
	public String getCertificatesNo() {
		return certificatesNo;
	}
	public void setCertificatesNo(String certificatesNo) {
		this.certificatesNo = certificatesNo;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getExpired() {
		return expired;
	}
	public void setExpired(String expired) {
		this.expired = expired;
	}
	public String getCvn() {
		return cvn;
	}
	public void setCvn(String cvn) {
		this.cvn = cvn;
	}
	public String getMchtOrderNo() {
		return mchtOrderNo;
	}
	public void setMchtOrderNo(String mchtOrderNo) {
		this.mchtOrderNo = mchtOrderNo;
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
	@Override
	public String toString() {
		return "ProtocolPayBindCardRequest [mchtOrderNo=" + mchtOrderNo + ", customerCode=" + customerCode
				+ ", memberId=" + memberId + ", userName=" + userName + ", phoneNum=" + phoneNum + ", bankCardNo="
				+ bankCardNo + ", bankCardType=" + bankCardType + ", certificatesType=" + certificatesType
				+ ", certificatesNo=" + certificatesNo + ", expired=" + expired + ", cvn=" + cvn + ", signSequence="
				+ "]";
	}
}
