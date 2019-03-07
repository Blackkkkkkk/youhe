package com.youhe.utils.pay.sdk.pay.domain.protocol;

import java.util.List;

import com.youhe.utils.pay.sdk.domain.Response;

/**
 * 协议查询返回
 * @author yjw
 *
 */
public class ProtocolQueryResponse extends Response{

    /**
     * 客户编码
     */
    private String customerCode;
    /**
     * 会员编号
     */
    private String memberId;
    
    /**
     * 商户协议列表
     */
    private List<ProtocolInfo> protocolList;
	
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

	public List<ProtocolInfo> getProtocolList() {
		return protocolList;
	}

	public void setProtocolList(List<ProtocolInfo> protocolList) {
		this.protocolList = protocolList;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
}
