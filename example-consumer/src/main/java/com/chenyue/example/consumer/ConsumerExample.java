package com.chenyue.example.consumer;

import com.chenyue.example.common.model.User;
import com.chenyue.example.common.service.UserService;
import com.chenyue.yuerpc.bootstrap.ConsumerBootstrap;
import com.chenyue.yuerpc.proxy.ServiceProxyFactory;

/**
 * @author chenyue7@foxmail.com
 * @date 17/5/2024
 * @description
 */
public class ConsumerExample {
    public static void main(String[] args) {
        // 服务提供者初始化 配置注册中心(etcd服务)
        ConsumerBootstrap.init();

        //获取代理
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("Chen Yue");
        //调用
        User newUser = userService.getUser(user);
        if (newUser != null) {
            System.out.println(newUser.getName());
        }else {
            System.out.println("user == null");
        }
    }
}
