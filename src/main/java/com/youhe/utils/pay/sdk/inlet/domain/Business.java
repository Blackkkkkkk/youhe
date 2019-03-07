package com.youhe.utils.pay.sdk.inlet.domain;

import java.util.HashMap;
import java.util.Map;

public class Business {
	
	private static final Map<String, String> map = new HashMap<>();
	
	static {
		map.put("SPLITTED", "BFZ");
		map.put("WX_NATIVE_PAY", "WxNative");
		map.put("ALI_NATIVE_PAY", "AliNative");
		map.put("WX_MICRO_PAY", "WxMicro");
		map.put("ALI_MICRO_PAY", "AliMicro");
		map.put("ALI_JSAPI_PAY", "AliJSAPI");
		map.put("WX_JSAPI_PAY", "WxJSAPI");
		map.put("WX_MWEB_PAY", "WxMWEB");
	}

	/*
	SPLITTED	被分账业务
	WX_NATIVE_PAY	微信主扫支付业务
	ALI_NATIVE_PAY	支付宝主扫支付业务
	WX_MICRO_PAY	微信被扫支付业务
	ALI_MICRO_PAY	支付宝被扫支付业务
	ALI_JSAPI_PAY	支付宝生活号支付业务
	WX_JSAPI_PAY	微信公众号支付业务
	WX_MWEB_PAY	微信H5支付业务
	ALI_MWEB_PAY	支付宝H5支付业务
	WX_APP_PAY	微信APP支付业务
	ALI_APP_PAY	支付宝APP支付业务
	*/

	private String code;//业务编码
	
	private String startDate;//业务有效期开始日期YYYYMMDD
	
	private String endDate;//业务有效期结束日期YYYYMMDD，如不填，表示长期有效

	private String ratioMode;//费率类型1：单笔固定费用 2：按交易金额比例

	/**
	 * 费率参数:
	   单笔固定费用，以一个非负整数指定每笔交易的费用，单位为分；
	   按交易金额比例，以一个非负整数指定每笔交易的比例，单位为万分之一。
	 */
	private String ratioParam;

	public Business() {}

	public Business(String code, String startDate, String endDate, String ratioMode, String ratioParam) {
		this.code = code;
		this.startDate = startDate;
		this.endDate = endDate;
		this.ratioMode = ratioMode;
		this.ratioParam = ratioParam;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getRatioMode() {
		return ratioMode;
	}

	public void setRatioMode(String ratioMode) {
		this.ratioMode = ratioMode;
	}

	public String getRatioParam() {
		return ratioParam;
	}

	public void setRatioParam(String ratioParam) {
		this.ratioParam = ratioParam;
	}

	public static Map<String, String> getMap() {
		return map;
	}

}
