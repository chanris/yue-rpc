package com.chenyue.springboot.starter.annotation;

import com.chenyue.yuerpc.constant.RpcConstant;
import com.chenyue.yuerpc.fault.tolerant.TolerantStrategy;
import com.chenyue.yuerpc.fault.tolerant.TolerantStrategyKeys;
import com.chenyue.yuerpc.loadbalancer.LoadBalancerKeys;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author chenyue7@foxmail.com
 * @date 17/5/2024
 * @description
 */
@Target({ElementType.FIELD}) // 注解的范围
@Retention(RetentionPolicy.RUNTIME) // 注解作用域
public @interface RpcReference {

    /**
     * 服务接口类
     */
    Class<?> interfaceClass() default void.class;

    /**
     * 版本
     */
    String serviceVersion() default RpcConstant.DEFAULT_SERVICE_VERSION;

    /**
     * 负载均衡
     */
    String loadBalancer() default LoadBalancerKeys.ROUND_ROBIN;

    /**
     * 容错策略
     */
    String retryStrategy() default TolerantStrategyKeys.FAIL_FAST;

    /**
     * 模拟调用
     */
    boolean mock() default false;
}
