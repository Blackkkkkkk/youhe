package com.youhe.utils.pay.sdk.pay.domain.WechatH5;

public class WxH5OrderGoods {
	private String name;
	private String number;
	private long amount;

	public WxH5OrderGoods() {
	}

	public WxH5OrderGoods(String name, String number, long amount) {
		this.setName(name);
		this.setNumber(number);
		this.setAmount(amount);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}
}