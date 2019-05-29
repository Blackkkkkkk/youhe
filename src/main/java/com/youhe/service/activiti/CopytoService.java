package com.youhe.service.activiti;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.youhe.dto.activiti.CopyToDto;
import com.youhe.entity.activiti.Copyto;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 任务抄送表 服务类
 * </p>
 *
 * @author Kalvin
 * @since 2019-05-22
 */
public interface CopytoService extends IService<Copyto> {

    /**
     * 设置已阅
     */
    void makeRead(Long id);

    /**
     * 待我阅读
     * @return
     */
    IPage<CopyToDto> getReadList(String userId, int size, int current);
}
