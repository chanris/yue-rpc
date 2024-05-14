package com.chenyue.yuerpc.registry;

import com.chenyue.yuerpc.config.RegistryConfig;
import com.chenyue.yuerpc.model.ServiceMetaInfo;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.KV;
import io.vertx.core.impl.ConcurrentHashSet;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author chenyue7@foxmail.com
 * @date 14/5/2024
 * @description
 */
public class EtcdRegistry implements Registry{

    private Client client;
    private KV kvClient;

    /**
     * 本机注册的节点 key 集合（用于维护续期）
     */
    private final Set<String> localRegisterNodeKeySet = new HashSet<>();

    /**
     * 注册中心服务缓存
     */
    private final RegistryServiceCache registryServiceCache = new RegistryServiceCache();

    /**
     * 正在监听的 key 集合
     */
    private final Set<String> watchingKeySet = new ConcurrentHashSet<>();

    /**
     * 根节点
     */
    private static final String ETCD_ROOT_PATH = "/rpc/";

    @Override
    public void init(RegistryConfig registryConfig) {
        client = Client.builder()
                .endpoints(registryConfig.getAddress())
    }

    @Override
    public void register(ServiceMetaInfo serviceMetaInfo) throws Exception {

    }

    @Override
    public void unRegister(ServiceMetaInfo serviceMetaInfo) {

    }

    @Override
    public List<ServiceMetaInfo> serviceDiscovery(String serviceKey) {
        return null;
    }

    @Override
    public void heartBeat() {

    }

    @Override
    public void watch(String serviceNodeKey) {

    }

    @Override
    public void destroy() {

    }
}
