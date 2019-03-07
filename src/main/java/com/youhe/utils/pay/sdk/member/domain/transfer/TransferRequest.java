package com.youhe.utils.pay.sdk.member.domain.transfer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TransferRequest {

	private String srcCustomerCode;
	private String targetCustomerCode;
	private String outTradeNo;
	private Long amount;
	private String remark;
	private List<SharedInfo> sharedInfoList;
	private String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");

	public String getSrcCustomerCode() {
		return srcCustomerCode;
	}

	public String getTargetCustomerCode() {
		return targetCustomerCode;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public Long getAmount() {
		return amount;
	}

	public String getRemark() {
		return remark;
	}

	public List<SharedInfo> getSharedInfoList() {
		return sharedInfoList;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setSrcCustomerCode(String srcCustomerCode) {
		this.srcCustomerCode = srcCustomerCode;
	}

	public void setTargetCustomerCode(String targetCustomerCode) {
		this.targetCustomerCode = targetCustomerCode;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setSharedInfoList(List<SharedInfo> sharedInfoList) {
		this.sharedInfoList = sharedInfoList;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	
	public void addSharedInfo(String customerCode, Long amount) {
		if(this.sharedInfoList == null) {
			this.sharedInfoList = new ArrayList<SharedInfo>();
		}
		this.sharedInfoList.add(new SharedInfo(customerCode, amount));
	}
	
	public class SharedInfo {
		
		private String customerCode;
		private Long amount;

		public SharedInfo() {
			
		}
		
		public SharedInfo(String customerCode, Long amount) {
			this.customerCode = customerCode;
			this.amount = amount;
		}
		
		public String getCustomerCode() {
			return customerCode;
		}
		public Long getAmount() {
			return amount;
		}
		public void setCustomerCode(String customerCode) {
			this.customerCode = customerCode;
		}
		public void setAmount(Long amount) {
			this.amount = amount;
		}
	}
}
