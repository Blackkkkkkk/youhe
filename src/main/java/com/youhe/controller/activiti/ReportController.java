package com.youhe.controller.activiti;

import com.youhe.controller.comm.BaseController;
import com.youhe.dto.activiti.ProcessReportDTO;
import com.youhe.service.activiti.ActivityService;
import com.youhe.utils.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 作用：报表中心<br>
 * 说明：(无)
 *
 * @author Kalvin
 * @Date 2019年05月28日 14:07
 */
@RestController
@RequestMapping(value = "report")
public class ReportController extends BaseController {

    @Autowired
    private ActivityService activityService;

    @RequiresPermissions("report:process:index")
    @GetMapping(value = "process/index")
    public ModelAndView process() {
        return new ModelAndView("activiti/report/process_report");
    }


    @RequiresPermissions("report:reimburse:index")
    @GetMapping(value = "reimburse/index")
    public ModelAndView reimburse() {
        return new ModelAndView("activiti/report/reimburse_report");
    }

    /**
     * 流程报表 数据接口
     * @param createTime 流程创建时间
     * @param archiveTime 流程归档时间
     * @return
     */
    @GetMapping(value = "process/data")
    public R processData(String createTime, String archiveTime) {
        List<ProcessReportDTO> processReportDTOS = activityService.listProcessReports(createTime, archiveTime);
        int totalCreateCount = 0, totalTodoCount = 0, totalArchiveCount = 0, totalCount;
        for (ProcessReportDTO processReportDTO : processReportDTOS) {
            totalCreateCount += processReportDTO.getCreateCount();
            totalTodoCount += processReportDTO.getTodoCount();
            totalArchiveCount += processReportDTO.getArchiveCount();
        }
        totalCount = totalCreateCount + totalTodoCount + totalArchiveCount;

        return R.ok().put("data", processReportDTOS)
                .put("totalCreateCount", totalCreateCount)
                .put("totalTodoCount", totalTodoCount)
                .put("totalArchiveCount", totalArchiveCount)
                .put("totalCount", totalCount);
    }

    @GetMapping(value = "reimburse/data")
    public R reimburseData(String year, String unit) {
        return R.ok().put("data", activityService.listReimburseReports(year, unit));
    }

}
