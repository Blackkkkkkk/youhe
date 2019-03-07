package com.youhe.utils.pay.sdk.pay.domain.WechatH5;

/**
 * @Author : Author
 * @Date : 2018/1/31 13:54
 * @Description :
 */
public class WxH5SceneInfo {
    private String type;
    private String wapURL;
    private String wapName;

    public WxH5SceneInfo() { }

    public WxH5SceneInfo(String type, String wapURL, String wapName) {
        this.type = type;
        this.wapURL = wapURL;
        this.wapName = wapName;
    }

    public String getType() { return type; }
    public void setType(String type) {
        this.type = type;
    }
    public String getWapURL() {
        return wapURL;
    }
    public void setWapURL(String wapURL) {
        this.wapURL = wapURL;
    }
    public String getWapName() {
        return wapName;
    }
    public void setWapName(String wapName) {
        this.wapName = wapName;
    }

}
