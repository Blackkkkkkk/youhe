package com.youhe.service.activiti;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youhe.common.Constant;
import com.youhe.entity.activiti.Delegate;
import com.youhe.mapper.activiti.DelegateMapper;
import com.youhe.utils.shiro.ShiroUserUtils;
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
    public IPage<Delegate> listDelegatePage(String processName, Integer type, int current, int size) {
        Page<Delegate> page = new Page<>(current, size);
        LambdaQueryWrapper<Delegate> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Delegate::getAssignee, ShiroUserUtils.getUserId());
        wrapper.eq(Delegate::getType, type);
        if (StrUtil.isNotBlank(processName))
            wrapper.like(Delegate::getProcessName, processName);
        return super.page(page, wrapper);
    }

    @Override
    public Delegate getDelegateAssigneeAndProcessDefId(String assignee, String processDefId, Integer type) {
        return super.getOne(new LambdaQueryWrapper<Delegate>()
                .eq(Delegate::getAssignee, assignee)
                .eq(Delegate::getProcessDefinitionId, processDefId)
                .eq(Delegate::getType, type));
    }

    @Override
    public Delegate getDelegateAttorneyAndProcessDefId(String attorney, String processDefId, Integer type) {
        return super.getOne(new LambdaQueryWrapper<Delegate>()
                .eq(Delegate::getAttorney, attorney)
                .eq(Delegate::getProcessDefinitionId, processDefId)
                .eq(Delegate::getType, type));
    }

    @Override
    public void removeDelegateByIdAndType(Long id, Integer type) {
        super.remove(new LambdaQueryWrapper<Delegate>().eq(Delegate::getId, id).eq(Delegate::getType, type));
    }

    @Override
    public IPage<Delegate> listAgencyAppPage(String processName, int current, int size) {
        String userId = String.valueOf(ShiroUserUtils.getUserId());
        Page<Delegate> page = new Page<>(current, size);
        LambdaQueryWrapper<Delegate> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Delegate::getType, Constant.DELEGATE_TYPE_0);
        wrapper.eq(Delegate::getAttorney, userId);  // 当前人为代理人
        wrapper.gt(Delegate::getEndTime, new Date());
        if (StrUtil.isNotBlank(processName))
            wrapper.like(Delegate::getProcessName, processName);
        return super.page(page, wrapper);
    }

}
