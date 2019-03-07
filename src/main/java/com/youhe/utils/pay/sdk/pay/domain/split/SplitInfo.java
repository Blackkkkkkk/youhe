package com.youhe.utils.pay.sdk.pay.domain.split;

/**
 * @Author : Author
 * @Date : 2018/2/6 17:18
 */
public class SplitInfo {

    private String customerCode;//分账目标商户在EFPS的客户编码
    private long amount;//分账金额,单位分
    private Integer isProcedureCustomer=0;//在分账中必填，且保证只有一个客户的该值为1。在包含分账信息的退款中不填。

    public SplitInfo() {    }

    public SplitInfo(String customerCode, long amount) {
        this.customerCode = customerCode;
        this.amount = amount;
    }

    public SplitInfo(String customerCode, long amount, Integer isProcedureCustomer) {
        this.customerCode = customerCode;
        this.amount = amount;
        this.isProcedureCustomer = isProcedureCustomer;
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
    public Integer getIsProcedureCustomer() {
        return isProcedureCustomer;
    }
    public void setIsProcedureCustomer(Integer isProcedureCustomer) {
        this.isProcedureCustomer = isProcedureCustomer;
    }

}



