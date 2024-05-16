package com.chenyue.yuerpc.fault.tolerant;

import com.chenyue.yuerpc.model.RpcResponse;

import java.util.Map;

/**
 * @author chenyue7@foxmail.com
 * @date 16/5/2024
 * @description 降级到其他服务 - 容错策略
 */
public class FailBackTolerantStrategy implements TolerantStrategy{
    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        // todo 24/5/16
        return null;
    }
}
