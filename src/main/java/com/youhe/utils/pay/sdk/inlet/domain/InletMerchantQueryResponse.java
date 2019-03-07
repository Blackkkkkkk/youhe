package com.youhe.utils.pay.sdk.inlet.domain;

import java.util.UUID;

import com.youhe.utils.pay.sdk.domain.Response;

/**
 * 子商户审核结果查询
 */
public class InletMerchantQueryResponse extends Response {

    private String customerInfoId; //被新增或修改的子商户资料ID
    private String name; //商户名称
    private String shortName; //商户简称
    private String auditResult; //审核结果
    private String auditMsg; //审核意见
    private String auditDateTime; //审核时间
    private String customerCode; //该子商户的客户编码
	private String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");

    public String getCustomerInfoId() {
        return customerInfoId;
    }

    public void setCustomerInfoId(String customerInfoId) {
        this.customerInfoId = customerInfoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getAuditResult() {
        return auditResult;
    }

    public void setAuditResult(String auditResult) {
        this.auditResult = auditResult;
    }

    public String getAuditMsg() {
        return auditMsg;
    }

    public void setAuditMsg(String auditMsg) {
        this.auditMsg = auditMsg;
    }

    public String getAuditDateTime() {
        return auditDateTime;
    }

    public void setAuditDateTime(String auditDateTime) {
        this.auditDateTime = auditDateTime;
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
