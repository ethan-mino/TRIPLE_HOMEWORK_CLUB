package com.triple.club.api.user.mapper;

import com.triple.club.api.user.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    UserEntity findUserByUsername(String username);
    int save(UserEntity user);
}
