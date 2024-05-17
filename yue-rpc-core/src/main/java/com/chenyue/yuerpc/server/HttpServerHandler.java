package com.chenyue.yuerpc.server;

import com.chenyue.yuerpc.serializer.Serializer;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerRequest;

/**
 * @author chenyue7@foxmail.com
 * @date 14/5/2024
 * @description HTTP请求处理器
 */
public class HttpServerHandler implements Handler<HttpServerRequest> {
    @Override
    public void handle(HttpServerRequest event) {
        // 指定序列器
        // final Serializer serializer =;

    }
}
