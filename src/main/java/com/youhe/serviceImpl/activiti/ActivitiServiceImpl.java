package com.youhe.serviceImpl.activiti;


import com.youhe.biz.activiti.ActivitiBiz;

import com.youhe.entity.activitiData.ACT_RE_MODEL_PROCDEF;
import com.youhe.service.activiti.ActivitiService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ActivitiServiceImpl implements ActivitiService {

    @Autowired
    private ActivitiBiz activitiBiz;


    @Override
    public List<ACT_RE_MODEL_PROCDEF> findList( ) {
        return activitiBiz.findList();
    }

}
