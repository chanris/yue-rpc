package com.chenyue.consumer;

import com.chenyue.springboot.starter.annotation.EnableYueRpc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author chenyue7@foxmail.com
 * @date 17/5/2024
 * @description
 */
@EnableYueRpc(needServer = false)
@SpringBootApplication
public class ExampleSpringbootConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExampleSpringbootConsumerApplication.class, args);
    }
}
