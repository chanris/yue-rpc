package com.chenyue.example.common.service;

import com.chenyue.example.common.model.User;

/**
 * @author chenyue7@foxmail.com
 * @date 17/5/2024
 * @description
 */
public interface UserService {

    User getUser(User user);

    default short getNumber() {
        return 1;
    }
}
