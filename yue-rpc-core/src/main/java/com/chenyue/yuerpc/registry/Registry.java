package com.chenyue.yuerpc.registry;

import com.chenyue.yuerpc.config.RegistryConfig;
import com.chenyue.yuerpc.model.ServiceMetaInfo;

import java.util.List;

/**
 * @author chenyue7@foxmail.com
 * @date 14/5/2024
 * @description 注册中心
 */
public interface Registry {
    /**
     * 初始化
     *
     * @param registryConfig
     */
    void init(RegistryConfig registryConfig);

    /**
     * 注册服务（服务器）
     *
     * @param serviceMetaInfo
     * @throws Exception
     */
    void register(ServiceMetaInfo serviceMetaInfo) throws Exception;

    /**
     * 注销服务
     *
     * @param serviceMetaInfo
     */
    void unRegister(ServiceMetaInfo serviceMetaInfo);

    /**
     * 服务发现（获得某服务的所有节点，消费端）
     *
     * @param serviceKey 服务键名
     */
    List<ServiceMetaInfo> serviceDiscovery(String serviceKey);

    /**
     * 心跳检测
     */
    void heartBeat();

    /**
     * 监听（消费端）
     * @param serviceNodeKey
     */
    void watch(String serviceNodeKey);

    /**
     * 服务销毁
     */
    void destroy();
}
