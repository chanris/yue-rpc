package com.chenyue.yuerpc.serializer;

import com.chenyue.yuerpc.spi.SpiLoader;

/**
 * @author chenyue7@foxmail.com
 * @date 14/5/2024
 * @description 序列化工厂（工厂模式，用于获取序列化对象）
 */
public class SerializerFactory {

    static {
        // 加载自定义Serializer
        SpiLoader.load(Serializer.class);
    }

    /**
     * 默认序列化器
     *
     */
    private static final Serializer DEFAULT_SERIALIZER = new JdkSerializer();

    /**
     * 获取实例
     *
     * @param key
     * @return
     */
    public static Serializer getInstance(String key) {
        return SpiLoader.getInstance(Serializer.class, key);
    }
}
