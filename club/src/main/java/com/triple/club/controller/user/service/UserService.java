package com.triple.club.controller.user.service;

import com.triple.club.controller.user.mapper.UserMapper;
import com.triple.club.controller.user.vo.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserMapper userMapper;
    public UserService(UserMapper userMapper){
        this.userMapper = userMapper;
    }

    @Transactional
    public User selectUserByUsername(String username){

        return null;
    }
}
