package com.chenyue.yuerpc.serializer;

import java.io.IOException;

/**
 * @author chenyue7@foxmail.com
 * @date 14/5/2024
 * @description
 */
public interface Serializer {
    /**
     * 序列化
     *
     * @param object
     * @return 字节流
     * @param <T>
     * @throws IOException
     */
    <T> byte[] serialize(T object) throws IOException;

    /**
     * 反序列化
     *
     * @param bytes
     * @param type
     * @return 对象
     * @param <T>
     * @throws IOException
     */
    <T> T deserialize(byte[] bytes, Class<T> type) throws IOException;
}
