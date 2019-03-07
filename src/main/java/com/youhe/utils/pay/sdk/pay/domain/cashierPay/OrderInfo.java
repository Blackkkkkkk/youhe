package com.youhe.utils.pay.sdk.pay.domain.cashierPay;
import java.util.ArrayList;
import java.util.List;

public class OrderInfo {
    private String id;
    private String businessType;
    private List<com.youhe.utils.pay.sdk.pay.domain.cashierPay.OrderGoods> goodsList = new ArrayList<com.youhe.utils.pay.sdk.pay.domain.cashierPay.OrderGoods>(10);

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getBusinessType() { return businessType; }
    public void setBusinessType(String businessType) { this.businessType = businessType; }
    public List<com.youhe.utils.pay.sdk.pay.domain.cashierPay.OrderGoods> getGoodsList() { return goodsList; }
    public void setGoodsList(List<com.youhe.utils.pay.sdk.pay.domain.cashierPay.OrderGoods> list) { this.goodsList = list; }
    public void addGood(com.youhe.utils.pay.sdk.pay.domain.cashierPay.OrderGoods good) { this.goodsList.add(good); }
}
