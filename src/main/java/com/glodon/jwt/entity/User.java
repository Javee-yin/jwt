package com.glodon.jwt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author: yinjw-b
 * @Date: 2021/1/25 15:34
 * @Description:
 */
@Data
@AllArgsConstructor
public class User {
    private String userName;
    private String password;

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
