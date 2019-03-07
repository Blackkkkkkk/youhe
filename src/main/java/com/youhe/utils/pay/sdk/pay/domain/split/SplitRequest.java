package com.youhe.utils.pay.sdk.pay.domain.split;

import java.util.List;
import java.util.UUID;

/**
 * @Author : Author
 * @Date : 2018/1/29 11:27
 * @Description :
 */
public class SplitRequest {

    private String outTradeNo;
    private String customerCode;
    private List<SplitInfo> splitInfoList;
    private String notifyUrl;
    private String attachData;
	private String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");

    public SplitRequest() { }

    public SplitRequest(String outTradeNo, String customerCode, List<SplitInfo> splitInfoList, String notifyUrl,
                      String attachData) {
        this.outTradeNo = outTradeNo;
        this.customerCode = customerCode;
        this.splitInfoList = splitInfoList;
        this.notifyUrl = notifyUrl;
        this.attachData = attachData;
    }

    public String getOutTradeNo() { return outTradeNo;}
    public void setOutTradeNo(String outTradeNo) { this.outTradeNo = outTradeNo;}
    public String getCustomerCode() { return customerCode;}
    public void setCustomerCode(String customerCode) { this.customerCode = customerCode; }
    public List<SplitInfo> getSplitInfoList() { return splitInfoList; }
    public void setSplitInfoList(List<SplitInfo> splitInfoList) { this.splitInfoList = splitInfoList; }
    public String getNotifyUrl() { return notifyUrl; }
    public void setNotifyUrl(String notifyUrl) { this.notifyUrl = notifyUrl; }
    public String getAttachData() { return attachData; }
    public void setAttachData(String attachData) { this.attachData = attachData; }
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

}
