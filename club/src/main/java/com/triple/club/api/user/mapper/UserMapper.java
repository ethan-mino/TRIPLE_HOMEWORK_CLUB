package com.triple.club.api.user.mapper;

import com.triple.club.api.user.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User findUserByUsername(String username);
    int save(User user);
}
