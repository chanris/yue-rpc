package com.chenyue.yuerpc.loadbalancer;

import com.chenyue.yuerpc.spi.SpiLoader;

/**
 * @author chenyue7@foxmail.com
 * @date 16/5/2024
 * @description 负载均衡器工厂（工厂模式，用于获取负载均衡器对象）
 */
public class LoadBalancerFactory {

    static {
        SpiLoader.load(LoadBalancer.class);
    }

    private static final LoadBalancer DEFAULT_LOAD_BALANCER = new RoundRobinLoadBalancer();

    public static LoadBalancer getInstance(String key) {
        return SpiLoader.getInstance(LoadBalancer.class, key);
    }
}
