package com.youhe.service.activiti;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youhe.entity.activiti.Delegate;

/**
 * <p>
 * 委托表 服务类
 * </p>
 *
 * @author Kalvin
 * @since 2019-05-23
 */
public interface DelegateService extends IService<Delegate> {

    IPage<Delegate> listDelegatePage(String processName, Integer type, int current, int size);

    Delegate getDelegateAssigneeAndProcessDefId(String assignee, String processDefId, Integer type);

    Delegate getDelegateAttorneyAndProcessDefId(String attorney, String processDefId, Integer type);

    /**
     * 根据ID和类型删除
     * @param id 主键ID
     * @param type 类型：0：代理申请；1：委托审批
     */
    void removeDelegateByIdAndType(Long id, Integer type);

    /**
     * 获取代理申请数据（分页）
     * @param processName
     * @param current
     * @param size
     * @return
     */
    IPage<Delegate> listAgencyAppPage(String processName, int current, int size);

}
