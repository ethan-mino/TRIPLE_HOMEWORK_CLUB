package com.triple.club.api.user.mapper;

import com.triple.club.api.user.vo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User findUserByUsername(String username);
    int save(User user);
}
