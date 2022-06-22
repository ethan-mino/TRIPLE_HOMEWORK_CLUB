package com.triple.club.Api.user.service;

import com.triple.club.Api.user.mapper.UserMapper;
import com.triple.club.Api.user.vo.User;
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
        return userMapper.selectUserByUsername(username);
    }

    public int createUser(User user){
        return userMapper.createUser(user);
    }

    public Boolean existsUser(String username){    // 아이디 중복 검사
        User user = userMapper.selectUserByUsername(username);
        return !(user == null);
    }
}
