package com.chenyue.consumer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author chenyue7@foxmail.com
 * @date 17/5/2024
 * @description
 */
@SpringBootTest
public class ExampleServiceImplTest {

    @Resource
    private ExampleServiceImpl exampleService;

    @Test
    void test01() {
        exampleService.test();
    }

}
