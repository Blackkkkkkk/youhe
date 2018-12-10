package com.youhe.serviceImpl.test;


import com.youhe.mapper.test.TestMapper;
import com.youhe.service.test.TestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestMapper testMapper;


    @Override
    public void delete(String userAccount) {
        testMapper.delete(userAccount);

    }


}
