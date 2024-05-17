package com.chenyue.yuerpc.server;

/**
 *
 * @author chenyue7@foxmail.com
 * @date 14/5/2024
 * @description Http 服务器接口
 */
public interface HttpServer {
    /**
     * 启动服务器
     *
     * @param port
     */
    void doStart(int port);
}
