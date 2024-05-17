package com.chenyue.consumer;

import com.chenyue.example.common.model.User;
import com.chenyue.example.common.service.UserService;
import com.chenyue.springboot.starter.annotation.RpcReference;
import org.springframework.stereotype.Service;

/**
 * @author chenyue7@foxmail.com
 * @date 17/5/2024
 * @description
 */
@Service
public class ExampleServiceImpl {

    @RpcReference
    private UserService userService;

    /**
     * 测试方法
     */
    public void test() {
        User user = new User();
        user.setName("chanris");
        assert userService != null;
        User resultUser = userService.getUser(user);
        assert resultUser != null;
        System.out.println(resultUser.getName());
    }
}
