package com.chenyue.yuerpc.fault.retry;

/**
 * @author chenyue7@foxmail.com
 * @date 16/5/2024
 * @description
 */
public interface RetryStrategyKeys {
    /**
     * 不重试
     */
    String NO = "no";

    /**
     * 固定时间间隔
     */
    String FIXED_INTERVAL = "fixedInterval";
}
