package com.youhe.service.activiti;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youhe.dto.activiti.CopyToDto;
import com.youhe.entity.activiti.Copyto;
import com.youhe.entity.activiti.Delegate;
import com.youhe.entity.user.User;
import com.youhe.mapper.activiti.CopytoMapper;
import com.youhe.mapper.user.UserMapper;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 任务抄送表 服务实现类
 * </p>
 *
 * @author Kalvin
 * @since 2019-05-22
 */
@Service
public class CopytoServiceImpl extends ServiceImpl<CopytoMapper, Copyto> implements CopytoService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CopytoMapper copytoMapper;
    @Autowired
    private TaskService taskService;

    @Override
    public void makeRead(Long id) {
        Copyto copyto = super.getById(id);
        Date createTime = copyto.getCreateTime();
        Date readTime = copyto.getReadTime();
      if(readTime==null) {
          readTime = new Date();
          // 计算时耗
          long duration = DateUtil.betweenMs(createTime, readTime);
          copyto.setDuration(duration);
      }
        copyto.setReadTime(readTime);

        super.updateById(copyto);
    }

    @Override
    public IPage<CopyToDto> getReadList(String userId, int size, int current) {
        Page<CopyToDto> page = new Page<>(current, size);
        List<CopyToDto> copyToDtoList=new ArrayList<CopyToDto>();
        List<CopyToDto> copyToDtos = copytoMapper.queryRead(userId,page);
        copyToDtos.forEach(copyToDto -> {

            CopyToDto   dto=new  CopyToDto();

            User userMapperName = userMapper.findName(copyToDto.getAssignee());
            dto.setAssignee(userMapperName.getUserName());

            //根据任务id获取从哪个环节发送过来
            String taskId = copyToDto.getTaskId();
            Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
            dto.setSendNode(task.getName());
            dto.setProcName(copyToDto.getProcName());
            dto.setTaskId(taskId);
            dto.setCreateTime(copyToDto.getCreateTime());
            dto.setId(copyToDto.getId());
            dto.setReadTime(copyToDto.getReadTime());
            copyToDtoList.add(dto);
        });

        return page.setRecords(copyToDtoList);
    }

}
