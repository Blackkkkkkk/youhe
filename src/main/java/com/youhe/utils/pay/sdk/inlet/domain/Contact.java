package com.youhe.utils.pay.sdk.inlet.domain;

import com.alibaba.fastjson.JSON;

public class Contact {

	Integer type;
	String name;
	String mobile;
	String email;
	String qq;

	public Contact(Integer type, String name, String mobile, String email, String qq) {
		this.type = type;
		this.name = name;
		this.mobile = mobile;
		this.email = email;
		this.qq = qq;
	}

	final static public Integer adminType=1;
	
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
	
}
