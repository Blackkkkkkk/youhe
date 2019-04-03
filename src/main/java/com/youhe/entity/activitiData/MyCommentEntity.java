package com.youhe.entity.activitiData;/**
 * @ClassName MyCommentEntity
 * @Description TODO
 * @Author xdn
 * @Date 2019/4/214:16
 * @Version 1.0
 */

import java.util.Date;

/**
 *@ClassName MyCommentEntity
 *@Description
 *@Author xdn
 *@Date 2019/4/214:16
 *@Version 1.0
 */
public class MyCommentEntity {

    private String curNoName;//当前环节名称

    private String id;

    private String userId;//用户名，对应的是act_hi_comment表的USER_ID_字段

    private String time;

    private String taskId;//任务id

    private String processInstanceId;//流程实例id

    private String message;//审批信息

    private String fullMessage;


    public String getCurNoName() {
        return curNoName;
    }

    public void setCurNoName(String curNoName) {
        this.curNoName = curNoName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFullMessage() {
        return fullMessage;
    }

    public void setFullMessage(String fullMessage) {
        this.fullMessage = fullMessage;
    }
}
