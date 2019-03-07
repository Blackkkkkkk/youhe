package com.youhe.utils.pay.sdk.pay.domain.wechatJSAPI;
import java.util.ArrayList;
import java.util.List;

public class WxJsOrderInfo {
    private String id;
    private String businessType;
    private List<WxJsOrderGoods> goodsList = new ArrayList<WxJsOrderGoods>(10);

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getBusinessType() { return businessType; }
    public void setBusinessType(String businessType) { this.businessType = businessType; }
    public List<WxJsOrderGoods> getGoodsList() { return goodsList; }
    public void setGoodsList(List<WxJsOrderGoods> list) { this.goodsList = list; }
    public void addGood(WxJsOrderGoods good) { this.goodsList.add(good); }
}
