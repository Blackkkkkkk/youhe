package com.youhe.service.activiti;

import com.youhe.entity.activiti.HiDelegate;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 委托历史表 服务类
 * </p>
 *
 * @author Kalvin
 * @since 2019-05-23
 */
public interface HiDelegateService extends IService<HiDelegate> {


    void saveHiDelegate(String assignee, String attorney, String taskId, Integer type);

}
