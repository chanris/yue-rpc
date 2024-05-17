package com.chenyue.provider;

import com.chenyue.example.common.model.User;
import com.chenyue.example.common.service.UserService;

/**
 * @author chenyue7@foxmail.com
 * @date 17/5/2024
 * @description
 */
public class UserServiceImpl implements UserService {
    @Override
    public User getUser(User user) {
        System.out.println("用户名: " + user.getName());
        return user;
    }
}
