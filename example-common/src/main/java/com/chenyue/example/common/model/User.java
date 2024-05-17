package com.chenyue.example.common.model;

import java.io.Serializable;

/**
 * @author chenyue7@foxmail.com
 * @date 17/5/2024
 * @description
 */
public class User implements Serializable {
    private String name;
    public String getName() { return name;}

    public void setName(String name) {
        this.name = name;
    }
}
