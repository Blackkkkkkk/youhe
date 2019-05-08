package com.youhe.mapper.activiti;

import org.apache.ibatis.annotations.Update;

/**
 * 作用：activity 接口<br>
 * 说明：(无)
 *
 * @author Kalvin
 * @Date 2019年05月08日 14:30
 */
public interface ActivityMapper {

    @Update("update ACT_HI_ACTINST set END_TIME_ = NOW() where PROC_INST_ID_ = #{procInstId} and ACT_ID_ = #{actId}")
    void updateHistoricActivityInstance(String procInstId, String actId);
}
