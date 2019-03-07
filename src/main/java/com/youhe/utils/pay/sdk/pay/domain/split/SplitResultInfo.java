package com.youhe.utils.pay.sdk.pay.domain.split;

/**
 * @Author : Author
 * @Date : 2018/2/8 16:51
 * @Description :
 */
public class SplitResultInfo {

    private String customerCode ;//分账目标商户在EFPS的客户编码
    private Long amount ;//分账金额
    private Long procedureFee ;//分账手续费

    public String getCustomerCode() { return customerCode; }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getProcedureFee() {
        return procedureFee;
    }

    public void setProcedureFee(Long procedureFee) {
        this.procedureFee = procedureFee;
    }
}
