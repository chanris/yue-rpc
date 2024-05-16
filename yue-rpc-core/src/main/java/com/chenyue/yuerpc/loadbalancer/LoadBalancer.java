package com.chenyue.yuerpc.loadbalancer;

import com.chenyue.yuerpc.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;

/**
 * @author chenyue7@foxmail.com
 * @date 16/5/2024
 * @description 负责均衡器（消费端使用）
 */
public interface LoadBalancer {

    /**
     * 选择服务调用
     */
    ServiceMetaInfo select(Map<String, Object> requestParams, List<ServiceMetaInfo> serviceMetaInfoList);
}
