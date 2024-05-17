package com.chenyue.springboot.starter.bootstrap;

import com.chenyue.springboot.starter.annotation.EnableYueRpc;
import com.chenyue.yuerpc.RpcApplication;
import com.chenyue.yuerpc.config.RpcConfig;
import com.chenyue.yuerpc.server.tcp.VertxTcpServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author chenyue7@foxmail.com
 * @date 17/5/2024
 * @description
 */
@Slf4j
public class RpcInitBootstrap implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        // 获取 EnableYueRpc
        boolean needServer = (boolean) importingClassMetadata.getAnnotationAttributes(EnableYueRpc.class.getName()).get("needServer");

        RpcApplication.init();

        // 全局配置
        final RpcConfig rpcConfig = RpcApplication.getRpcConfig();

        // 启动服务器
        if (needServer) {
            VertxTcpServer vertxTcpServer = new VertxTcpServer();
            vertxTcpServer.doStart(rpcConfig.getServerPort());
        } else {
            log.info("不启动 server");
        }
    }
}
