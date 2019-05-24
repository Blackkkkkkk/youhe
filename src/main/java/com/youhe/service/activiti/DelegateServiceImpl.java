package com.youhe.service.activiti;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youhe.entity.activiti.Delegate;
import com.youhe.mapper.activiti.DelegateMapper;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 委托表 服务实现类
 * </p>
 *
 * @author Kalvin
 * @since 2019-05-23
 */
@Service
public class DelegateServiceImpl extends ServiceImpl<DelegateMapper, Delegate> implements DelegateService {

    @Override
    public IPage<Delegate> listDelegatePage(String processName, int current, int size) {
        Page<Delegate> page = new Page<>(current, size);
        LambdaQueryWrapper<Delegate> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(processName))
            wrapper.eq(Delegate::getProcessName, processName);
        return super.page(page, wrapper);
    }

    @Override
    public Delegate getDelegateAssigneeAndProcessDefId(String assignee, String processDefId) {
        return super.getOne(new LambdaQueryWrapper<Delegate>()
                .eq(Delegate::getAssignee, assignee).eq(Delegate::getProcessDefinitionId, processDefId));
    }

    @Override
    public boolean isDelegate(String assignee, String processDefId) {
        Delegate delegate = this.getDelegateAssigneeAndProcessDefId(assignee, processDefId);
        Date startTime = delegate.getStartTime();
        Date endTime = delegate.getEndTime();
        long l = DateUtil.betweenMs(startTime, endTime);
        return l >= 0;
    }
}
