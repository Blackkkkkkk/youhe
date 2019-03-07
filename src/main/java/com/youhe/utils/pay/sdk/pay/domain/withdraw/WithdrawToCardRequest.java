package com.youhe.utils.pay.sdk.pay.domain.withdraw;

import java.io.Serializable;
/**
 * 提现请求数据
 * @author yjw
 *
 */
public class WithdrawToCardRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 商户订单号
	 */
	private String outTradeNo;
	/**
	 * 客户ID
	 */
	private String customerCode;
	/**
	 * 提现金额
	 */
	private Long amount;
	/**
	 * 支付币种
	 */
	private String payCurrency;
	/**
	 * 渠道类型
	 */
	private String channelType;
	/**
	 * 提现结果通知地址
	 */
	private String notifyUrl;
	/**
     * 备注
     */
    private String remark;
	/**
     * 账户类型
     */
    private String bankAccountType;
	
    /**
     * 开户人姓名
     */
    private String bankUserName;
    /**
     * 开户人身份证
     */
    private String bankUserCert;
    /**
     * 银行卡号
     */
    private String bankCardNo;
    /**
     * 银行名称
     */
    private String bankName;
    /**
     * 开户行省
     */
    private String bankProvince;
    /**
     * 开户行市
     */
    private String bankCity;
    /**
     * 开户行支行
     */
    private String bankSub;
    /**
     * 联行号
     */
    private String bankNo;
    
    /**
     * 是否垫资 1-垫资,0-不垫资
     */
    private String isAdvanceFund;
    
	public String getIsAdvanceFund() {
		return isAdvanceFund;
	}
	public void setIsAdvanceFund(String isAdvanceFund) {
		this.isAdvanceFund = isAdvanceFund;
	}
	public String getBankNo() {
		return bankNo;
	}
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
    /**
     * 随机字符串
     */
    private String nonceStr;
    
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public String getPayCurrency() {
		return payCurrency;
	}
	public void setPayCurrency(String payCurrency) {
		this.payCurrency = payCurrency;
	}
	public String getChannelType() {
		return channelType;
	}
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getBankAccountType() {
		return bankAccountType;
	}
	public void setBankAccountType(String bankAccountType) {
		this.bankAccountType = bankAccountType;
	}
	public String getBankUserName() {
		return bankUserName;
	}
	public void setBankUserName(String bankUserName) {
		this.bankUserName = bankUserName;
	}
	public String getBankUserCert() {
		return bankUserCert;
	}
	public void setBankUserCert(String bankUserCert) {
		this.bankUserCert = bankUserCert;
	}
	public String getBankCardNo() {
		return bankCardNo;
	}
	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankProvince() {
		return bankProvince;
	}
	public void setBankProvince(String bankProvince) {
		this.bankProvince = bankProvince;
	}
	public String getBankCity() {
		return bankCity;
	}
	public void setBankCity(String bankCity) {
		this.bankCity = bankCity;
	}
	public String getBankSub() {
		return bankSub;
	}
	public void setBankSub(String bankSub) {
		this.bankSub = bankSub;
	}
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
}
