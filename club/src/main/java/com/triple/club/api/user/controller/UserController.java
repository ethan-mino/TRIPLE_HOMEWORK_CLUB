package com.triple.club.api.user.controller;

import com.triple.club.api.exception.InvalidInputException;
import com.triple.club.api.user.service.UserService;
import com.triple.club.api.user.vo.User;
import com.triple.club.api.util.ApiInfoResponse;
import com.triple.club.config.security.JwtTokenProvider;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;
import javax.validation.Valid;
import java.sql.SQLException;

@RestController
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public UserController(UserService userService,
                          PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider){
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiInfoResponse<String>> login(@RequestBody User user) throws AuthenticationException {
        String username = user.getUsername();
        User queriedUser = userService.findUserByUsername(username);

        // 아이디 또는 비밀번호가 일치하지 않는 경우
        if(queriedUser == null || !passwordEncoder.matches(user.getPassword(), queriedUser.getPassword())){
            throw new AuthenticationException();
        }else{  // 아이디와 비밀번호가 일치하는 경우
            ApiInfoResponse<String> apiResponse = new ApiInfoResponse<>();

            String token = jwtTokenProvider.createToken(username);  // token 생성
            apiResponse.setData(token);
            return ResponseEntity.ok(apiResponse);
        }
    }

    @PostMapping("/signup") // 회원가입
    public ResponseEntity<ApiInfoResponse<User>> signup(@Valid @RequestBody User user, BindingResult bindingResult)  throws SQLException{
        ApiInfoResponse<User> apiResponse = new ApiInfoResponse<>();

        if(bindingResult.hasErrors()){
            throw new InvalidInputException();
        }else{
            String username = user.getUsername();
            Boolean isUsernameDup = userService.existsByUsername(username); // 아이디 중복 검사

            if(!isUsernameDup){  // 아이디가 중복되지 않은 경우
                String password = user.getPassword();
                String encodedPassword = passwordEncoder.encode(password);  // 비밀번호 암호화

                user.setPassword(encodedPassword);
                int createCnt = userService.save(user);

                if(createCnt == 1){ // 계정이 생성된 경우
                    User createdUser = userService.findUserByUsername(username);
                    apiResponse.setData(createdUser);
                    return ResponseEntity.ok(apiResponse);
                }else{  // 계정 생성에 실패한 경우
                    throw new SQLException();
                }
            }else{  // 아이디가 중복된 경우
                throw new DuplicateKeyException(null);
            }
        }
    }
}