package com.youhe.activiti.ext;

import org.activiti.engine.impl.cfg.IdGenerator;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * 【作用】自定义流程实例ID生成策略<br>
 * 【说明】（无）
 *
 * @author kalvin
 * @Date 2019年03月26日 10:31
 */
@Service
public class UUIDGenerator implements IdGenerator {

    @Override
    public String getNextId() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
