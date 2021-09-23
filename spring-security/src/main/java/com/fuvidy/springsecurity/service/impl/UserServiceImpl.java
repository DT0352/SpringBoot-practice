package com.fuvidy.springsecurity.service.impl;

import com.fuvidy.springsecurity.mapper.UserMapper;
import com.fuvidy.springsecurity.model.UserEntity;
import com.fuvidy.springsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void save(UserEntity user) {
        userMapper.save(user);
    }
}
