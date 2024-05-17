package com.chenyue.yuerpc.server;

import io.vertx.core.Vertx;

/**
 * @author chenyue7@foxmail.com
 * @date 14/5/2024
 * @description Vertx HTTP 服务器
 */
public class VertxHttpServer implements HttpServer{

    /**
     * 启动服务器
     *
     * @param port
     */
    @Override
    public void doStart(int port) {
        // 创建 Vert.x 实例
        Vertx vertx = Vertx.vertx();

        // 创建 HTTP 服务器
        io.vertx.core.http.HttpServer server = vertx.createHttpServer();

        // 处理请求
        server.requestHandler(new HttpServerHandler());

        server.listen(port, result -> {
           if (result.succeeded()) {
               System.out.println("Server is now listening on port " + port);
           }else {
               System.out.println("Failed to start server: " + result.cause());
           }
        });
    }
}
