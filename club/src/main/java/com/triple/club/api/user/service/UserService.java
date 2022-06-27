package com.triple.club.api.user.service;

import com.triple.club.api.user.mapper.UserMapper;
import com.triple.club.api.user.entity.UserEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserMapper userMapper;
    public UserService(UserMapper userMapper){
        this.userMapper = userMapper;
    }

    @Transactional(readOnly = true)
    public UserEntity findUserByUsername(String username){
        return userMapper.findUserByUsername(username);
    }

    @Transactional(readOnly = true)
    public Boolean existsByUsername(String username){    // 아이디 중복 검사
        UserEntity user = userMapper.findUserByUsername(username);
        return !(user == null);
    }

    @Transactional
    public int save(UserEntity user){
        return userMapper.save(user);
    }
}
