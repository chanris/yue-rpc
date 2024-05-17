package com.chenyue.yuerpc.registry;

import com.chenyue.yuerpc.spi.SpiLoader;

/**
 * @author chenyue7@foxmail.com
 * @date 16/5/2024
 * @description 注册中心工厂（用于获取注册中心对象）
 */
public class RegistryFactory {
    static {
        SpiLoader.load(Registry.class);
    }

    private static final Registry DEFAULT_REGISTRY = new EtcdRegistry();

    public static Registry getInstance(String key) {
        return SpiLoader.getInstance(Registry.class, key);
    }
}
