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
    /**
     * 注册中心类别
     */
    private String registry = RegistryKeys.ETCD;

    /**
     * 注册中心地址
     */
    private String address = "http://localhost:2380";

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 超时时间（单位毫秒）
     */
    private Long timeout = 10000L;
}
