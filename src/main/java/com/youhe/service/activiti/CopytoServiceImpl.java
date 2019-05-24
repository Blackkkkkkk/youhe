package com.youhe.service.activiti;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youhe.entity.activiti.Copyto;
import com.youhe.mapper.activiti.CopytoMapper;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;

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

    @Override
    public void makeRead(Long id) {
        Copyto copyto = super.getById(id);
        Date createTime = copyto.getCreateTime();
        Date readTime = copyto.getReadTime();
        if (readTime == null) {
            readTime = new Date();
            // 计算时耗
            long duration = DateUtil.betweenMs(createTime, readTime);
            copyto.setDuration(duration);
        }
        super.updateById(copyto);
    }
}
