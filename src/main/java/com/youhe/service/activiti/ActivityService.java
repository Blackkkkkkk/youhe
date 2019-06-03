package com.youhe.service.activiti;

import com.youhe.dto.activiti.ProcessReportDTO;
import com.youhe.dto.activiti.ReimburseReportDTO;
import com.youhe.dto.activiti.VarInstDTO;

import java.util.List;

/**
 * 作用：activity服务类<br>
 * 说明：(无)
 *
 * @author Kalvin
 * @Date 2019年05月30日 9:42
 */
public interface ActivityService {

    /**
     * 流程报表列表
     * @param startTime 流程开始时间
     * @param archiveTime   流程归档时间
     * @return
     */
    List<ProcessReportDTO> listProcessReports(String startTime, String archiveTime);

    /**
     * 根据流程定义ID或变量值获取流程变量列表
     * @param processDefId 流程定义ID
     * @param varName 变量名
     * @param varValue 变量值（这个是模糊查询）
     * @return
     */
    List<VarInstDTO> listVarInst(String processDefId, String varName, String varValue);

    /**
     * 报销报表
     * @param year 年份
     * @param unit 部门/单位
     * @return
     */
    ReimburseReportDTO listReimburseReports(String year, String unit);
}
