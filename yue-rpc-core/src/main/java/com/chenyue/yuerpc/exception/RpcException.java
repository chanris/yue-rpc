package com.chenyue.yuerpc.exception;

/**
 * @author chenyue7@foxmail.com
 * @date 14/5/2024
 * @description rpc异常类
 */
public class RpcException extends RuntimeException {
    public RpcException(String message) { super(message);}
}
