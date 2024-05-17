package com.chenyue.yuerpc.bootstrap;

import com.chenyue.yuerpc.RpcApplication;
import com.chenyue.yuerpc.config.RegistryConfig;
import com.chenyue.yuerpc.config.RpcConfig;
import com.chenyue.yuerpc.model.ServiceMetaInfo;
import com.chenyue.yuerpc.model.ServiceRegisterInfo;
import com.chenyue.yuerpc.registry.LocalRegistry;
import com.chenyue.yuerpc.registry.Registry;
import com.chenyue.yuerpc.registry.RegistryFactory;
import com.chenyue.yuerpc.server.tcp.VertxTcpServer;

import java.util.List;

/**
 * @author chenyue7@foxmail.com
 * @date 17/5/2024
 * @description 服务提供者启动类（初始化）
 */
public class ProviderBootstrap {

    /**
     * 初始化
     * @param serviceRegisterInfoList
     * 从ProviderExample 获得 需要注册的服务
     */
    public static void init(List<ServiceRegisterInfo<?>> serviceRegisterInfoList) {
        // RPC 框架初始化（配置和注册中心）
        RpcApplication.init();
        // 全局配置
        final RpcConfig rpcConfig = RpcApplication.getRpcConfig();

        // 注册服务
        for (ServiceRegisterInfo<?> serviceRegisterInfo : serviceRegisterInfoList) {
            String serviceName = serviceRegisterInfo.getServiceName();
            // 本地注册
            LocalRegistry.register(serviceName, serviceRegisterInfo.getImplClass());

            //注册服务到注册中心
            RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
            Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry()); // 获得注册中心实例 EtcdRegistry
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
            serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
            try {
                registry.register(serviceMetaInfo);
            }catch (Exception e) {
                throw new RuntimeException(serviceName + " 服务注册失败", e);
            }
        }

        //启动服务
        VertxTcpServer vertxTcpServer = new VertxTcpServer();
        vertxTcpServer.doStart(rpcConfig.getServerPort());
    }
}
