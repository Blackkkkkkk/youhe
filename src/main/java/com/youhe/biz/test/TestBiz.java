package com.youhe.biz.test;


import com.youhe.mapper.test.TestMapper;
import com.youhe.mapper.user.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class TestBiz {
    private Logger log = LoggerFactory.getLogger(TestBiz.class);

    @Autowired
    private TestMapper testMapper;


    public void delete(String userName) {
        log.debug("TestBiz.delete()");
        try {
            testMapper.delete(userName);
        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }


}
