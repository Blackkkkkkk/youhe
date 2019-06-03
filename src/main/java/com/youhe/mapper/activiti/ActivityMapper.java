package com.youhe.mapper.activiti;

import com.youhe.dto.activiti.ProcessReportDTO;
import com.youhe.dto.activiti.VarInstDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

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

    List<ProcessReportDTO> selectProcessReports(@Param("startTime") String startTime, @Param("archiveTime") String archiveTime);

    /**
     * 根据流程定义ID或变量值查询流程变量列表
     * @param processDefId 流程定义ID
     * @param varName 变量名
     * @param varValue 变量值（这个是模糊查询）
     * @return
     */
    List<VarInstDTO> selectVarInstList(String processDefId, String varName, String varValue);

    List<String> selectAllVarInstProInstId(String processDefId, String varName, String varValue);

    List<String> selectVarInstProInstId(List<String> processInstIds, String varName, String varValue);
}
