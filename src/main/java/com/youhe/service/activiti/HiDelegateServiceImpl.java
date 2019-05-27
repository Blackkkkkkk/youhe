package com.youhe.service.activiti;

import com.youhe.entity.activiti.HiDelegate;
import com.youhe.mapper.activiti.HiDelegateMapper;
import com.youhe.service.activiti.HiDelegateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 委托历史表 服务实现类
 * </p>
 *
 * @author Kalvin
 * @since 2019-05-23
 */
@Service
public class HiDelegateServiceImpl extends ServiceImpl<HiDelegateMapper, HiDelegate> implements HiDelegateService {

    @Override
    public void saveHiDelegate(String assignee, String attorney, String taskId, Integer type) {
        super.save(new HiDelegate(assignee, attorney, taskId, type));
    }
}
