package com.youhe.service.activiti;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.youhe.activiti.engine.MyProcessEngine;
import com.youhe.dto.activiti.ProcessReportDTO;
import com.youhe.dto.activiti.ReimburseReportDTO;
import com.youhe.dto.activiti.VarInstDTO;
import com.youhe.mapper.activiti.ActivityMapper;
import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.history.HistoricVariableInstanceQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 作用：activity服务实现类<br>
 * 说明：(无)
 *
 * @author Kalvin
 * @Date 2019年05月30日 9:43
 */
@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private HistoryService historyService;

    @Autowired
    private ActivityMapper activityMapper;

    @Override
    public List<ProcessReportDTO> listProcessReports(String startTime, String archiveTime) {
        return activityMapper.selectProcessReports(startTime, archiveTime);
    }

    @Override
    public List<VarInstDTO> listVarInst(String processDefId, String varName, String varValue) {
        return activityMapper.selectVarInstList(processDefId, varName, varValue);
    }

    @Override
    public ReimburseReportDTO listReimburseReports(String year, String unit) {
        ReimburseReportDTO reimburseReportDTO = new ReimburseReportDTO();
        reimburseReportDTO.setSubtext(year + "年度 " + (StrUtil.isNotBlank(unit) ? unit : ""));
        String[] monthArr = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};

        List<String> months = new ArrayList<>();
        List<Integer> values = new ArrayList<>();
        List<HashMap<String, Object>> mps = new ArrayList<>();
        HashMap<String, Object> hashMap;
        int i = 0;
        for (String month : monthArr) {
            i++;
            hashMap = new HashMap<>();
            months.add(i + "月");
            List<String> vailProInstIds = activityMapper
                    .selectAllVarInstProInstId("renexpense:1:dc55066c6c824e24899f190c152fea67", "startTime", year + "-" + month);
            int totalMonthPrice = 0;    // 当前月报销总金额

            // 统计当前月报销总金额
            if (CollectionUtil.isNotEmpty(vailProInstIds)) {
                HistoricVariableInstanceQuery historicVariableInstanceQuery = historyService.createHistoricVariableInstanceQuery();
                historicVariableInstanceQuery.variableName("totalPrice");
                if (StrUtil.isNotBlank(unit)) {
                    // 当前月当前报销部门所有流程实例
                    List<String> proInstIdsForUnit = activityMapper.selectVarInstProInstId(vailProInstIds, "unit", unit);
                    for (String proInstId : proInstIdsForUnit) {
                        historicVariableInstanceQuery.processInstanceId(proInstId);
                        HistoricVariableInstance historicVariableInstance = historicVariableInstanceQuery.singleResult();
                        Integer totalPrice = Integer.valueOf((String) historicVariableInstance.getValue()); // 报销金额
                        totalMonthPrice += totalPrice;
                    }

                } else {
                    // 当前月所有报销流程实例
                    for (String proInstId : vailProInstIds) {
                        historicVariableInstanceQuery.processInstanceId(proInstId);
                        HistoricVariableInstance historicVariableInstance = historicVariableInstanceQuery.singleResult();
                        Integer totalPrice = Integer.valueOf((String) historicVariableInstance.getValue()); // 报销金额
                        totalMonthPrice += totalPrice;
                    }
                }
            }
            values.add(totalMonthPrice);
            hashMap.put("value", totalMonthPrice);
            hashMap.put("name", i + "月");
            mps.add(hashMap);
        }

        reimburseReportDTO.setMonths(months);
        reimburseReportDTO.setPrices(values);
        reimburseReportDTO.setMps(mps);
        return reimburseReportDTO;
    }

}
