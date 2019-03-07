package com.youhe.utils.pay.sdk.pay.domain.withdraw;

import java.util.UUID;

/**
 * 子商户提现请求数据
 *
 */
public class Withdraw4SubMerchantRequest {
	/**
	 * 商户的提现订单号
	 */
	private String outTradeNo;
	
	/**
	 * 父客户编码，外部商户发送请求时，不需设置该字段，系统使用API鉴权完成后添加到HTTP头部客户编码
	 */
	private String customerCode;

	/**
	 * 子客户编码，并不需要在请求体中指定父商户编码，而是从请求头中获取
	 */
	private String subCustomerCode;

	/**
	 * 提现金额
	 */
	private Long payAmount;
	/**
	 * 支付币种
	 */
	private String payCurrency;

	/**
	 * 提现结果通知地址
	 */
	private String notifyUrl;
	/**
     * 备注
     */
    private String remark;

	private String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");

	public Withdraw4SubMerchantRequest() {

	}

	public Withdraw4SubMerchantRequest(String outTradeNo,  String subCustomerCode, Long payAmount, String payCurrency, String notifyUrl, String remark) {
		this.outTradeNo = outTradeNo;
		this.subCustomerCode = subCustomerCode;
		this.payAmount = payAmount;
		this.payCurrency = payCurrency;
		this.notifyUrl = notifyUrl;
		this.remark = remark;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}



	public Long getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Long payAmount) {
		this.payAmount = payAmount;
	}

	public String getPayCurrency() {
		return payCurrency;
	}

	public void setPayCurrency(String payCurrency) {
		this.payCurrency = payCurrency;
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


	public String getSubCustomerCode() {
		return subCustomerCode;
	}

	public void setSubCustomerCode(String subCustomerCode) {
		this.subCustomerCode = subCustomerCode;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	
	
}
