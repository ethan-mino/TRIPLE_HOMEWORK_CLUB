package com.triple.club.controller.user.mapper;

import com.triple.club.controller.user.vo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User selectUserByUsername(String username);
    int createUser(User user);
}
