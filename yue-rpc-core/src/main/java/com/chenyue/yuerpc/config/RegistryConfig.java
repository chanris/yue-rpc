package com.chenyue.yuerpc.config;

import com.chenyue.yuerpc.registry.RegistryKeys;
import lombok.Data;

/**
 * @author chenyue7@foxmail.com
 * @date 14/5/2024
 * @description
 */
@Data
public class RegistryConfig {
    private String registry = RegistryKeys.ETCD;
}
