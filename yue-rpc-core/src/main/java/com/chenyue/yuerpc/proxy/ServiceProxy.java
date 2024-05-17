package com.chenyue.yuerpc.proxy;

import cn.hutool.core.collection.CollUtil;
import com.chenyue.yuerpc.RpcApplication;
import com.chenyue.yuerpc.config.RpcConfig;
import com.chenyue.yuerpc.constant.RpcConstant;
import com.chenyue.yuerpc.fault.retry.RetryStrategy;
import com.chenyue.yuerpc.fault.retry.RetryStrategyFactory;
import com.chenyue.yuerpc.fault.tolerant.TolerantStrategy;
import com.chenyue.yuerpc.fault.tolerant.TolerantStrategyFactory;
import com.chenyue.yuerpc.loadbalancer.LoadBalancer;
import com.chenyue.yuerpc.loadbalancer.LoadBalancerFactory;
import com.chenyue.yuerpc.model.RpcRequest;
import com.chenyue.yuerpc.model.RpcResponse;
import com.chenyue.yuerpc.model.ServiceMetaInfo;
import com.chenyue.yuerpc.registry.Registry;
import com.chenyue.yuerpc.registry.RegistryFactory;
import com.chenyue.yuerpc.server.tcp.VertxTcpClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenyue7@foxmail.com
 * @date 14/5/2024
 * @description 服务代理（JDK代理）
 *
 * 消费者 代理方法，构造RpcRequest、构造Vertx Client 发送RPC请求 ，
 * 返回RpcResponse结果, 解析RpcResponse返回数据。
 */
public class ServiceProxy implements InvocationHandler {

    /**
     * 调用代理
     *
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 构造请求
        String serviceName = method.getDeclaringClass().getName();
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(serviceName)
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();

        // 从注册中心获取服务提供者请求地址
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        Registry registry = RegistryFactory.getInstance(rpcConfig.getRegistryConfig().getRegistry());
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName(serviceName);
        serviceMetaInfo.setServiceVersion(RpcConstant.DEFAULT_SERVICE_VERSION);
        List<ServiceMetaInfo> serviceMetaInfoList = registry.serviceDiscovery(serviceMetaInfo.getServiceKey());
        if (CollUtil.isEmpty(serviceMetaInfoList)) {
            throw new RuntimeException("暂无服务地址");
        }

        //负载均衡
        LoadBalancer loadBalancer = LoadBalancerFactory.getInstance(rpcConfig.getLoadBalancer());
        // 将调用方法名（请求路径）作为负载均衡参数
        Map<String, Object> requestParams = new HashMap<>();
        requestParams.put("methodName", rpcRequest.getMethodName());
        ServiceMetaInfo selectServiceMetaInfo = loadBalancer.select(requestParams, serviceMetaInfoList);

        // rpc请求
        RpcResponse rpcResponse;
        try {
            RetryStrategy retryStrategy = RetryStrategyFactory.getInstance(rpcConfig.getRetryStrategy());
            rpcResponse = retryStrategy.doRetry(() -> VertxTcpClient.doRequest(rpcRequest, selectServiceMetaInfo));
        }catch (Exception e) {
            //容错机制
            TolerantStrategy tolerantStrategy = TolerantStrategyFactory.getInstance(rpcConfig.getTolerantStrategy());
            rpcResponse = tolerantStrategy.doTolerant(null, e);
        }
        return rpcResponse.getData();
    }
}
