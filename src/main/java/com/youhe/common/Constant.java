package com.youhe.common;

/**
 * @author jhy
 * 系统级常量
 */
public class Constant {

    /** http请求状态常量 */
    public static final int OK = 0;
    public static final int FAIL = 500;
    public static final String OK_MSG = "请求成功";
    public static final String FAIL_MSG = "请求失败";

    //SHIRO加密
    public static final String HASH_ALGORITHM = "SHA-1";//加密算法
    public static final int HASH_INTERATIONS = 1024;//迭代次数


    //退款异步通知地址
    public static final String REFUND_NOTIFYURL = "http://238r9j8196.wicp.vip/touristShop/refundResult";//迭代次数

    /** activiti 相关常量 */
    public final static String FLOW_VARIABLE_KEY = "flowVariable";  // 流程变量key值

    public final static String TASK_DATA_KEY = "taskData"; // 流程任务流转数据key值

    public final static String FORM_DATA_KEY = "formData";    // 存放表单数据的key值

    public final static String NEXT_USER_ID_KEY = "nextUser";   // 下一审批人key值

    public final static String DEFAULT_AGREE_COMMENT = "同意";
    public final static String DEFAULT_DISAGREE_COMMENT = "不同意";

    public final static String DEFAULT_FORM_NAME = "activiti/default";  // activiti默认表单
    public final static String FORM_PRFIX = "activiti/form/";   // actviti业务表单前缀

}
