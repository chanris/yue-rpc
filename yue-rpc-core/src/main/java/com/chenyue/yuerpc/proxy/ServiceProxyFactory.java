package com.chenyue.yuerpc.proxy;

import com.chenyue.yuerpc.RpcApplication;

import java.lang.reflect.Proxy;

/**
 * @author chenyue7@foxmail.com
 * @date 17/5/2024
 * @description 服务代理工厂（工厂模式，用于创建代理对象）
 */
public class ServiceProxyFactory {

    /**
     * 根据服务类获得代理对象
     */
    public static <T> T getProxy(Class<T> serviceClass) {
        if (RpcApplication.getRpcConfig().isMock()) {
            return getMockProxy(serviceClass);
        }

        return (T) Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class[]{serviceClass},
                new ServiceProxy());
    }

    /**
     * 根据服务类获取 Mock 代理对象
     *
     * @param serviceClass
     * @return
     * @param <T>
     */
    public static <T> T getMockProxy(Class<T> serviceClass) {
        return (T) Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class[]{serviceClass},
                new MockServiceProxy());
    }
}
