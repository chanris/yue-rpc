package com.chenyue.yuerpc.model;

import com.chenyue.yuerpc.constant.RpcConstant;
import lombok.Data;

/**
 * @author chenyue7@foxmail.com
 * @date 14/5/2024
 * @description 服务元信息
 */
@Data
public class ServiceMetaInfo {
    /**
     * 服务名称
     */
    private String serviceName;
    /**
     * 服务版本号
     */
    private String serviceVersion = RpcConstant.DEFAULT_SERVICE_VERSION;
}
