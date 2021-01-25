package com.glodon.jwt.controller;

import com.glodon.jwt.entity.CheckResult;
import com.glodon.jwt.entity.User;
import com.glodon.jwt.utils.JwtUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @Author: yinjw-b
 * @Date: 2021/1/25 15:33
 * @Description:
 */
@RestController
@RequestMapping("/jwt")
public class LoginController {
    // 模拟的用户信息
    private static List<User> users = new ArrayList<>();
    static{
        users.add(new User("yinjw", "yinjw"));
        users.add(new User("yinwei", "yinwei"));
        users.add(new User("Javee", "Javee"));
    }

    @PostMapping("/login")
    public String login(String username, String password){
        Optional<User> first = users.stream().filter(user1 -> user1.getUserName().equals(username)).findFirst();
        if(first.isPresent()){
            User user = first.get();
            if(user.getPassword().equals(password)){
                //把token返回给客户端-->客户端保存至cookie-->客户端每次请求附带cookie参数
                String JWT = JwtUtils.createJWT("1", username, 1611560404241l);
                return "success: " + JWT;
            }else{
                return "error";
            }
        }else{
            return "error";
        }
    }

    @PostMapping("/description")
    public CheckResult description(String jwt) {
        return JwtUtils.validateJwt(jwt);
    }
}
