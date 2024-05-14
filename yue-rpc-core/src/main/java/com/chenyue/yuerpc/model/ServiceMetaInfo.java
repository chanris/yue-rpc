package com.chenyue.yuerpc.model;

import cn.hutool.core.util.StrUtil;
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
    /**
     * 服务地址
     */
    private String serviceHost;
    /**
     * 服务端口
     */
    private Integer servicePort;
    /**
     * 服务分组（暂未实现）
     */
    private String serviceGroup = "default";

    /**
     * 获得服务键名
     *
     * @return
     */
    public String getServiceKey() {
        return String.format("%s:%s", serviceName, serviceVersion);
    }

    /**
     * 获取服务注册节点键名
     *
     * @return
     */
    public String getServiceNodeKey() {
        return String.format("%s/%s:%s", getServiceKey(), serviceHost, servicePort);
    }

    /**
     * 获取完整的服务地址
     *
     * @return
     */
    public String getServiceAddress() {
        if (!StrUtil.contains(serviceHost, "http")) {
            return String.format("http://%s:%s", serviceHost, servicePort);
        }
        return String.format("%s:%s", serviceHost, servicePort);
    }

}
