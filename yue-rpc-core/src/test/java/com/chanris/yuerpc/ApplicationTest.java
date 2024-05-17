package com.chanris.yuerpc;

import com.chenyue.yuerpc.RpcApplication;
import com.chenyue.yuerpc.config.RpcConfig;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

/**
 * @author chenyue7@foxmail.com
 * @date 16/5/2024
 * @description
 */

public class ApplicationTest {
    @Test
    void test01() throws NoSuchMethodException {
        Class<RpcApplication> clazz = RpcApplication.class;
        Method init = clazz.getMethod("init", RpcConfig.class);
        System.out.println(init.getName());
    }
}
