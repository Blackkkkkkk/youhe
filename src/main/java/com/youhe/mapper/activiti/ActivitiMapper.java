package com.youhe.mapper.activiti;

import com.youhe.entity.activitiData.ACT_RE_MODEL_PROCDEF;
import com.youhe.entity.user.User;

import java.util.List;


public interface ActivitiMapper {


    //查找流程信息
    List<ACT_RE_MODEL_PROCDEF> findList( );


}
