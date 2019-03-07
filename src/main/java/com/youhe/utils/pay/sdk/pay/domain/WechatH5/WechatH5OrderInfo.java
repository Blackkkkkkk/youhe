package com.youhe.utils.pay.sdk.pay.domain.WechatH5;

import java.util.ArrayList;
import java.util.List;

public class WechatH5OrderInfo {
	private String id;
	private String businessType;
	private List<com.youhe.utils.pay.sdk.pay.domain.WechatH5.WxH5OrderGoods> goodsList = new ArrayList<com.youhe.utils.pay.sdk.pay.domain.WechatH5.WxH5OrderGoods>(10);

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public List<com.youhe.utils.pay.sdk.pay.domain.WechatH5.WxH5OrderGoods> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<com.youhe.utils.pay.sdk.pay.domain.WechatH5.WxH5OrderGoods> list) {
		this.goodsList = list;
	}

	public void addGood(com.youhe.utils.pay.sdk.pay.domain.WechatH5.WxH5OrderGoods good) {
		this.goodsList.add(good);
	}
}
