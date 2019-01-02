package com.youhe.biz.activiti;


import com.youhe.entity.activitiData.ACT_RE_MODEL_PROCDEF;
import com.youhe.mapper.activiti.ActivitiMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class ActivitiBiz {
    private Logger log = LoggerFactory.getLogger(ActivitiBiz.class);

    @Autowired
    private ActivitiMapper activitiMapper;


    public List<ACT_RE_MODEL_PROCDEF> findList() {
        log.debug("findList:");
        return activitiMapper.findList();
    }

}
