package com.youhe.utils.pay.sdk.inlet.domain;

import com.youhe.utils.pay.sdk.inlet.domain.Business;
import com.youhe.utils.pay.sdk.inlet.domain.Contact;

import java.util.List;
import java.util.UUID;

/**
 * Created by adm on 2018/1/23.
 */
public class InletMerchantRequest {

    /**
     * 商户编号
     * NotEmpty
     */
    private String customerCode;

    /**
     * 商户名称 Size:1-100
     * NotEmpty
     */
    private String name;

    /**
     * 商户简称 Size:1-100
     * NotEmpty
     */
    private String shortName;

    /**
     *  商户手机号 Size:11
     *  NotEmpty
     */
    private String mobile;

    /**
     * 商户电话号码  Size: max :20
     * 可选
     */
    private String telephone;

    /**
     * 商户类别：
         10：个体商户
         20：企业（集团）商户
         30：渠道商户
         40：内部商户
         50：小微商户
     * NotEmpty
     */
    private String type;

    /**
     * 归属省市区 编号由EFPS提供
     * NotEmpty
     */
    private String areaCode;

    /**
     * 是否三证合一
     * String 1：是  0： 否
     * NotEmpty
     */
    private String useUSCC;

    /**
     * 文件 营业执照图片
     * NotEmpty
     */
    private String businessLinenseImage;

    /**
     * 营业地址
     */
    private String businessAddress;

    /**
     * 营业执照号 Size: max: 30
     * NotEmpty
     */
    private String businessLicenseNo;

    /**
     * 营业执照有效期 YYYY-MM-DD或长期
     * NotEmpty
     */
    private String businessLicenesExpiredDate;

    /**
     * 法人或经营者姓名
     * NotEmpty
     */
    private String lealPersonName;

    /**
     * 法人或经营者证件类型   1：身份证
     * NotEmpty
     */
    private String lealPersonIdentificationType;

    /**
     * 法人或经营者证件号码
     * NotEmpty
     */
    private String lealPersonIdentificationNo;

    /**
     * 法人或经营者证件有效期截止日期
     * NotEmpty
     */
    private String lealPersonIdentificationExpiredDate;

    /**
     * 图片文件 法人身份证正面图片
     * NotEmpty
     */
    private String lealPersonIdentificationImage1;

    /**
     * 图片文件 法人身份证反面图片 NotEmpty
     */
    private String lealPersonIdentificationImage2;

    /**
     * 结算周期 单位为天
     * NotEmpty
     */
    private String settCircle;

    /**
     * 结算账户类型 1：对公 2：对私
     * NotEmpty
     */
    private String bankAccountType;

    /**
     * 结算开户行名称
     * NotEmpty
     */
    private String bankName;

    /**
     * 银行开户名称/持卡人姓名
     * NotEmpty
     */
    private String customerNameInBank;

    /**
     * 银行账号 /卡号
     * NotEmpty
     */
    private String accountNo;

    /**
     * 联行号由EFPS提供联行号表
     * NotEmpty
     */
    private String bankLineNumber;

    /**
     * 结算目标 1：结算到银行卡
               2： 结算到易票联账户
               目前只能填1
     * NotEmpty
     */
    private String settTarget;

    /**
     *  异步通知审核结果使用的URL
     *  NotEmpty
     */
    private String notifyURL;

    /**
     * 商户开通的业务参数
     */
    private List<Business> businessList;

    /**
     * 联系人信息列表
     */
    private List<Contact> contactList;

    /**
     * 结算模式 取值 T/D
     */
    private String settMode;

    /**
     * 随机字符串
     */
	private String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");

    /**
     * 注册地址
     */
    private String registeredAddress  ;

    public String getRegisteredAddress() {
        return registeredAddress;
    }

    public void setRegisteredAddress(String registeredAddress) {
        this.registeredAddress = registeredAddress;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getUseUSCC() {
        return useUSCC;
    }

    public void setUseUSCC(String useUSCC) {
        this.useUSCC = useUSCC;
    }

    public String getBusinessLinenseImage() {
        return businessLinenseImage;
    }

    public void setBusinessLinenseImage(String businessLinenseImage) {
        this.businessLinenseImage = businessLinenseImage;
    }

    public String getBusinessLicenseNo() {
        return businessLicenseNo;
    }

    public void setBusinessLicenseNo(String businessLicenseNo) {
        this.businessLicenseNo = businessLicenseNo;
    }

    public String getBusinessLicenesExpiredDate() {
        return businessLicenesExpiredDate;
    }

    public void setBusinessLicenesExpiredDate(String businessLicenesExpiredDate) {
        this.businessLicenesExpiredDate = businessLicenesExpiredDate;
    }

    public String getLealPersonName() {
        return lealPersonName;
    }

    public void setLealPersonName(String lealPersonName) {
        this.lealPersonName = lealPersonName;
    }

    public String getLealPersonIdentificationType() {
        return lealPersonIdentificationType;
    }

    public void setLealPersonIdentificationType(String lealPersonIdentificationType) {
        this.lealPersonIdentificationType = lealPersonIdentificationType;
    }

    public String getLealPersonIdentificationNo() {
        return lealPersonIdentificationNo;
    }

    public void setLealPersonIdentificationNo(String lealPersonIdentificationNo) {
        this.lealPersonIdentificationNo = lealPersonIdentificationNo;
    }

    public String getLealPersonIdentificationExpiredDate() {
        return lealPersonIdentificationExpiredDate;
    }

    public void setLealPersonIdentificationExpiredDate(String lealPersonIdentificationExpiredDate) {
        this.lealPersonIdentificationExpiredDate = lealPersonIdentificationExpiredDate;
    }

    public String getLealPersonIdentificationImage1() {
        return lealPersonIdentificationImage1;
    }

    public void setLealPersonIdentificationImage1(String lealPersonIdentificationImage1) {
        this.lealPersonIdentificationImage1 = lealPersonIdentificationImage1;
    }

    public String getLealPersonIdentificationImage2() {
        return lealPersonIdentificationImage2;
    }

    public void setLealPersonIdentificationImage2(String lealPersonIdentificationImage2) {
        this.lealPersonIdentificationImage2 = lealPersonIdentificationImage2;
    }

    public String getSettCircle() {
        return settCircle;
    }

    public void setSettCircle(String settCircle) {
        this.settCircle = settCircle;
    }

    public String getBankAccountType() {
        return bankAccountType;
    }

    public void setBankAccountType(String bankAccountType) {
        this.bankAccountType = bankAccountType;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCustomerNameInBank() {
        return customerNameInBank;
    }

    public void setCustomerNameInBank(String customerNameInBank) {
        this.customerNameInBank = customerNameInBank;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getBankLineNumber() {
        return bankLineNumber;
    }

    public void setBankLineNumber(String bankLineNumber) {
        this.bankLineNumber = bankLineNumber;
    }

    public String getSettTarget() {
        return settTarget;
    }

    public void setSettTarget(String settTarget) {
        this.settTarget = settTarget;
    }

    public String getNotifyURL() {
        return notifyURL;
    }

    public void setNotifyURL(String notifyURL) {
        this.notifyURL = notifyURL;
    }

    public List<Business> getBusinessList() {
        return businessList;
    }

    public void setBusinessList(List<Business> businessList) {
        this.businessList = businessList;
    }

    public List<Contact> getContactList() {
        return contactList;
    }

    public void setContactList(List<Contact> contactList) {
        this.contactList = contactList;
    }

    public String getSettMode() {
        return settMode;
    }

    public void setSettMode(String settMode) {
        this.settMode = settMode;
    }
}
