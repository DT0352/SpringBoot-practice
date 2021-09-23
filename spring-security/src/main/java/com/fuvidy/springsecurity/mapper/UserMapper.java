package com.fuvidy.springsecurity.mapper;

import com.fuvidy.springsecurity.model.UserEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    void save(UserEntity user);
}
