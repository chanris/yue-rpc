package com.chenyue.yuerpc.fault.tolerant;

import com.chenyue.yuerpc.spi.SpiLoader;
import lombok.extern.slf4j.Slf4j;

/**
 * @author chenyue7@foxmail.com
 * @date 16/5/2024
 * @description 容错策略工厂（工厂模式，用于获取容错策略对象）
 */
@Slf4j
public class TolerantStrategyFactory {
    static {
        SpiLoader.load(TolerantStrategy.class); // 加载实例Class对象
    }

    /**
     * 默认容错策略
     */
    private static final TolerantStrategy DEFAULT_RETRY_STRATEGY = new FailFastTolerantStrategy();

    /**
     * 获取实例
     *
     * @param key
     * @return
     */
    public static TolerantStrategy getInstance(String key) {
        return SpiLoader.getInstance(TolerantStrategy.class, key);
    }
}
