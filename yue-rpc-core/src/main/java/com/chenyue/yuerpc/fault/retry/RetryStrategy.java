package com.chenyue.yuerpc.fault.retry;

import com.chenyue.yuerpc.model.RpcResponse;

import java.util.concurrent.Callable;

/**
 * @author chenyue7@foxmail.com
 * @date 16/5/2024
 * @description 重试策略
 */
public interface RetryStrategy {

    /**
     * 重试
     *
     * @param callable
     * @return
     * @throws Exception
     */
    RpcResponse doRetry(Callable<RpcResponse> callable) throws Exception;
}
