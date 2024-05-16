package com.chenyue.yuerpc.fault.retry;

import com.chenyue.yuerpc.model.RpcResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

/**
 * 不重试 - 重试策略
 *
 * @author chenyue7@foxmail.com
 * @date 16/5/2024
 * @description
 */
@Slf4j
public class NoRetryStrategy implements RetryStrategy{

    /**
     * 重试
     *
     * @param callable
     * @return
     * @throws Exception
     */
    @Override
    public RpcResponse doRetry(Callable<RpcResponse> callable) throws Exception {
        return callable.call();
    }
}
