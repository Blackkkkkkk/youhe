package com.youhe.entity.activitiData;/**
 * @ClassName ProdefTask
 * @Description
 * @Author xdn
 * @Date 2019/3/2119:09
 * @Version 1.0
 */



import java.io.Serializable;



/**
 *@ClassName ProdefTask
 *@Description 许迪能
 *@Author xdn
 *@Date 2019/3/2119:09
 *@Version 1.0
 */
public class ProdefTask  implements Cloneable, Serializable {

    private static final long serialVersionUID = 3160094850506230197L;
    private String name_;//流程名称(标题)
    private String name;//当前环节名称
    private String startUserName;//发起人
    private String preUserName;//上一个环节审批人
    private String createTime;//创建时间
    private String endTime;//结束时间
    private String taskId;//任务id
    private String startUserId;//发起人id
    private static ProdefTask per = new ProdefTask();

    private ProdefTask(){

    }

    public static ProdefTask getOnePerson() {
        try {

            return  (ProdefTask)per.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String getName_() {
        return name_;
    }

    public void setName_(String name_) {
        this.name_ = name_;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartUserName() {
        return startUserName;
    }

    public void setStartUserName(String startUserName) {
        this.startUserName = startUserName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getStartUserId() {
        return startUserId;
    }

    public void setStartUserId(String startUserId) {
        this.startUserId = startUserId;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getPreUserName() {
        return preUserName;
    }

    public void setPreUserName(String preUserName) {
        this.preUserName = preUserName;
    }
}
