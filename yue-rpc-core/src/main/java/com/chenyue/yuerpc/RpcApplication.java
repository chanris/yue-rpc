package com.chenyue.yuerpc;

import com.chenyue.yuerpc.config.RegistryConfig;
import com.chenyue.yuerpc.config.RpcConfig;
import com.chenyue.yuerpc.constant.RpcConstant;
import com.chenyue.yuerpc.registry.Registry;
import com.chenyue.yuerpc.registry.RegistryFactory;
import com.chenyue.yuerpc.util.ConfigUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author chenyue7@foxmail.com
 * @date 14/5/2024
 * @description RPC框架应用，相对于holder，存放了项目全局用到的变量。双检锁单例模式实现
 */
@Slf4j
public class RpcApplication {

    private static volatile RpcConfig rpcConfig;

    public static void init(RpcConfig newRpcConfig) {
        rpcConfig = newRpcConfig;
        log.info("rpc init, config = {}", newRpcConfig.toString());
        //注册中心初始化
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig(); // 获得注册中心的配置
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry()); // EtcdRegistry
        registry.init(registryConfig); // 初始化jetcd客户端，与etcd服务器建立连接，（heartBeat）并维护etcd中的服务节点信息
        log.info("registry init, config = {}", registryConfig);
        // 创建并注册 Shutdown Hook, JVM退出时执行操作
        Runtime.getRuntime().addShutdownHook(new Thread(registry::destroy));
    }

    public static void init() {
        RpcConfig newRpcConfig;
        try {
            newRpcConfig = ConfigUtils.loadConfig(RpcConfig.class, RpcConstant.DEFAULT_CONFIG_PREFIX);
        }catch (Exception e) {
            // 配置加载失败，使用默认值
            newRpcConfig = new RpcConfig();
        }
        init(newRpcConfig);
    }

    public static RpcConfig getRpcConfig() {
        if (rpcConfig == null) {
            synchronized (RpcApplication.class) {
                if (rpcConfig == null) {
                    init();
                }
            }
        }
        return rpcConfig;
    }
}
