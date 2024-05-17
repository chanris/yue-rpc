package com.chenyue.yuerpc.server.tcp;

import cn.hutool.core.util.IdUtil;
import com.chenyue.yuerpc.RpcApplication;
import com.chenyue.yuerpc.model.RpcRequest;
import com.chenyue.yuerpc.model.RpcResponse;
import com.chenyue.yuerpc.model.ServiceMetaInfo;
import com.chenyue.yuerpc.protocol.*;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetSocket;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author chenyue7@foxmail.com
 * @date 16/5/2024
 * @description
 */
public class VertxTcpClient {

    public static RpcResponse doRequest(RpcRequest rpcRequest, ServiceMetaInfo serviceMetaInfo) throws InterruptedException, ExecutionException {
        // 发送TCP请求
        Vertx vertx = Vertx.vertx();
        NetClient netClient = vertx.createNetClient();
        CompletableFuture<RpcResponse> responseFuture = new CompletableFuture<>();
        netClient.connect(serviceMetaInfo.getServicePort(), serviceMetaInfo.getServiceHost(), result -> {
            if (!result.succeeded()) {
                System.err.println("Failed to connect to TCP server");
                return;
            }
            NetSocket socket = result.result();
            //发送数据
            //构造消息
            ProtocolMessage<RpcRequest> protocolMessage = new ProtocolMessage<>();
            ProtocolMessage.Header header = new ProtocolMessage.Header();
            header.setMagic(ProtocolConstant.PROTOCOL_MAGIC);
            header.setVersion(ProtocolConstant.PROTOCOL_VERSION);
            header.setSerializer((byte) ProtocolMessageSerializerEnum.getEnumByValue(RpcApplication.getRpcConfig().getSerializer()).getKey());
            header.setType((byte) ProtocolMessageTypeEnum.REQUEST.getKey());
            //生成全局请求ID
            header.setRequestId(IdUtil.getSnowflakeNextId());
            protocolMessage.setHeader(header);
            protocolMessage.setBody(rpcRequest);
            //编码请求
            try {
                Buffer encodeBuffer = ProtocolMessageEncoder.encode(protocolMessage);
                socket.write(encodeBuffer);
            }catch (IOException e) {
                throw new RuntimeException("协议消息编码失败");
            }

            // 接收响应
            TcpBufferHandlerWrapper bufferHandlerWrapper = new TcpBufferHandlerWrapper(
                    buffer -> {
                        try {
                            ProtocolMessage<RpcResponse> rpcResponseProtocolMessage =
                                    (ProtocolMessage<RpcResponse>)ProtocolMessageDecoder.decode(buffer);
                            responseFuture.complete(rpcResponseProtocolMessage.getBody());
                        }catch (Exception e) {
                            throw new RuntimeException("协议消息解码错误", e);
                        }
                    }
            );
            socket.handler(bufferHandlerWrapper);
        });

        RpcResponse rpcResponse = responseFuture.get();
        netClient.close();
        return rpcResponse;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setServiceName("com.chenyue.yuerpc.common.UserService");
        rpcRequest.setMethodName("getUserName");
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceHost("localhost");
        serviceMetaInfo.setServicePort(8888);
        RpcResponse rpcResponse = VertxTcpClient.doRequest(rpcRequest, serviceMetaInfo);
        System.out.println(rpcResponse);
    }
}
