package com.triple.club.controller.user.controller;

import com.triple.club.controller.user.vo.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @PostMapping("/user")
    public ResponseEntity<User> signup(User user){

        return null;
    }
}
