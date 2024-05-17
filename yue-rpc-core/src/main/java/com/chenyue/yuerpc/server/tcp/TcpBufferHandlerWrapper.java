package com.chenyue.yuerpc.server.tcp;

import com.chenyue.yuerpc.protocol.ProtocolConstant;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.parsetools.RecordParser;



/**
 * @author chenyue7@foxmail.com
 * @date 16/5/2024
 * @description TCP 消息处理器包装
 * 装饰者模式，使用 recordParser 对原有的 buffer 处理能力进行增强
 */
public class TcpBufferHandlerWrapper implements Handler<Buffer> {

    /**
     * 解析器，用于解决半包、粘包问题
     */
    private final RecordParser recordParser;

    public TcpBufferHandlerWrapper(Handler<Buffer> bufferHandler) {
        recordParser = initRecordParser(bufferHandler);
    }

    @Override
    public void handle(Buffer buffer) {
        recordParser.handle(buffer);
    }

    /**
     * 初始化解析器
     *
     * @param bufferHandler
     * @return
     */
    private RecordParser initRecordParser(Handler<Buffer> bufferHandler) {
        // 构造 parser
        RecordParser parser = RecordParser.newFixed(ProtocolConstant.MESSAGE_HEADER_LENGTH); // 固定接收17个字节组成一个记录

        parser.setOutput(new Handler<Buffer>() {
            // 初始化
            int size = -1; // 消息体的字节长度，第一次读取为-1，表示还没读取过
            Buffer resultBuffer = Buffer.buffer(); // 新的、空的buffer流
            @Override
            public void handle(Buffer buffer) {
                if (-1 == size) {
                    // 读取消息头长度
                    size = buffer.getInt(13); // 获得协议消息体的长度
                    parser.fixedSizeMode(size);
                    resultBuffer.appendBuffer(buffer);
                }else {
                    // 2. 然后读取消息体
                    // 写入体消息到结果
                    resultBuffer.appendBuffer(buffer);
                    //已拼接成完整的 buffer，执行处理 TcpServerHandler.handle
                    bufferHandler.handle(resultBuffer);
                    //重置一轮
                    parser.fixedSizeMode(ProtocolConstant.MESSAGE_HEADER_LENGTH);
                    size = -1;
                    resultBuffer = Buffer.buffer();
                }
            }
        });
        return parser;
    }
}
