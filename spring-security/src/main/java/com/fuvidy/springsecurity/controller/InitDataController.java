package com.fuvidy.springsecurity.controller;

import com.fuvidy.springsecurity.model.UserEntity;
import com.fuvidy.springsecurity.service.UserService;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InitDataController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;
    @GetMapping("initUserData")
    public String initUserData() {
        // 普通用户
        UserEntity user = new UserEntity();
        user.setUserName("user");
        user.setPassWord(passwordEncoder.encode("user"));
        userService.save(user);
         //管理员
        UserEntity admin=new UserEntity();
        admin.setUserName("admin");
        admin.setPassWord(passwordEncoder.encode("admin"));
        userService.save(admin);
        return "success!!!";
    }
}
