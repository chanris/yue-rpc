package com.chenyue.provider;

import cn.hutool.system.UserInfo;
import com.chenyue.example.common.service.UserService;
import com.chenyue.yuerpc.bootstrap.ProviderBootstrap;
import com.chenyue.yuerpc.model.ServiceRegisterInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenyue7@foxmail.com
 * @date 17/5/2024
 * @description
 */
public class ProviderExample {
    public static void main(String[] args) {
        //要注册的服务
        List<ServiceRegisterInfo<?>> serviceRegisterInfoList = new ArrayList<>();
        ServiceRegisterInfo<UserService> serviceRegisterInfo = new ServiceRegisterInfo<>(UserService.class.getName(), UserServiceImpl.class);
        serviceRegisterInfoList.add(serviceRegisterInfo);

        // 服务提供者初始化
        ProviderBootstrap.init(serviceRegisterInfoList);
    }
}
