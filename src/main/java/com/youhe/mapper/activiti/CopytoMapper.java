package com.youhe.mapper.activiti;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.youhe.dto.activiti.CopyToDto;
import com.youhe.entity.activiti.Copyto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;


import java.util.List;

/**
 * <p>
 * 任务抄送表 Mapper 接口
 * </p>
 *
 * @author Kalvin
 * @since 2019-05-22
 */
public interface CopytoMapper extends BaseMapper<Copyto> {

    /**
     *
     * @param userId
     * @return
     */
    List<CopyToDto> queryRead(String userId,IPage page);

}
