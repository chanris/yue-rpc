package com.chenyue.springboot.starter.annotation;

import com.chenyue.springboot.starter.bootstrap.RpcConsumerBootstrap;
import com.chenyue.springboot.starter.bootstrap.RpcInitBootstrap;
import com.chenyue.springboot.starter.bootstrap.RpcProviderBootstrap;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author chenyue7@foxmail.com
 * @date 17/5/2024
 * @description 启动rpc注解
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({RpcInitBootstrap.class, RpcProviderBootstrap.class, RpcConsumerBootstrap.class}) // todo 24/5/17 检验效果
public @interface EnableYueRpc {

    /**
     * 需要启动server
     */
    boolean needServer() default true;
}
