package com.triple.club.Api.user.service;

import com.triple.club.Api.user.dto.CustomUserDetails;
import com.triple.club.Api.user.vo.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    public CustomUserDetailsService(UserService userService){
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findUserByUsername(username);

        if(user == null){
            throw new UsernameNotFoundException("Username Not Found");
        }

        CustomUserDetails customUserDetails = CustomUserDetails.builder()
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .build();

        return customUserDetails;
    }
}
