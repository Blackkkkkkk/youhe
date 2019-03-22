package com.youhe.entity.activitiData;/**
 * @ClassName ProdefTask
 * @Description TODO
 * @Author xdn
 * @Date 2019/3/2119:09
 * @Version 1.0
 */

import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.DelegationState;
import org.activiti.engine.task.Task;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 *@ClassName ProdefTask
 *@Description 许迪能
 *@Author xdn
 *@Date 2019/3/2119:09
 *@Version 1.0
 */
public class ProdefTask  implements Cloneable{


    private String name_;//流程名称
    private String name;//当前节点名称
    private String assignee;//提交人
    private String createTime;//创建时间
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

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
