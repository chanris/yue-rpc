package com.chenyue.yuerpc.loadbalancer;

/**
 * @author chenyue7@foxmail.com
 * @date 16/5/2024 负载均衡器键名常量
 * @description
 */
public interface LoadBalancerKeys {
    String ROUND_ROBIN = "roundRobin";
    String RANDOM = "random";
    String CONSISTENT_HASH = "consistentHash";
}
