package com.chenyue.provider;

import com.chenyue.springboot.starter.annotation.EnableYueRpc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author chenyue7@foxmail.com
 * @date 17/5/2024
 * @description
 */
@EnableYueRpc
@SpringBootApplication
public class ExampleSpringbootProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExampleSpringbootProviderApplication.class, args);
    }

}
