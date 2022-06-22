package com.triple.club.Api.user.mapper;

import com.triple.club.Api.user.vo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User findUserByUsername(String username);
    int createUser(User user);
}
