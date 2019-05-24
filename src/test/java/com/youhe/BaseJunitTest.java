package com.youhe;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 作用：junit测试基类<br>
 * 说明：单元测试类继承这个类即可
 *
 * @author Kalvin
 * @Date 2019年05月24日 10:08
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public abstract class BaseJunitTest {
    protected final static Logger LOGGER = LoggerFactory.getLogger(BaseJunitTest.class);
}
