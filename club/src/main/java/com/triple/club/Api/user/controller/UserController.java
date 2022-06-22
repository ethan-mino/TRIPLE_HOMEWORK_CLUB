package com.triple.club.Api.user.controller;

import com.triple.club.Api.Util.ApiResponse;
import com.triple.club.Api.Util.ApiResult.ApiAuthResult;
import com.triple.club.Api.Util.ApiResult.ApiBasicResult;
import com.triple.club.Api.user.service.UserService;
import com.triple.club.Api.user.vo.User;
import com.triple.club.config.security.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
    public ResponseEntity<ApiResponse<String>> login(@RequestBody User user){
        ApiResponse<String> apiResponse = new ApiResponse<>();

        String username = user.getUsername();
        User queriedUser = userService.selectUserByUsername(username);

        // 아이디 또는 비밀번호가 일치하지 않는 경우
        if(queriedUser == null || !passwordEncoder.matches(user.getPassword(), queriedUser.getPassword())){
            apiResponse.setResult(ApiAuthResult.ERR_INVALID_ACCOUNT);
        }else{  // 아이디와 비밀번호가 일치하는 경우
            String token = jwtTokenProvider.createToken(username);  // token 생성
            apiResponse.setResult(ApiBasicResult.INF_SUCCESS);
            apiResponse.setData(token);
        }

        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/signup") // 회원가입
    public ResponseEntity<ApiResponse<User>> signup(@Valid @RequestBody User user, BindingResult bindingResult){
        ApiResponse<User> apiResponse = new ApiResponse<>();

        if(bindingResult.hasErrors()){
            apiResponse.setResult(ApiBasicResult.ERR_INVALID_VALUE);
        }else{
            String username = user.getUsername();
            Boolean isUsernameDup = userService.existsUser(username); // 아이디 중복 검사

            if(!isUsernameDup){  // 아이디가 중복되지 않은 경우
                String password = user.getPassword();
                String encodedPassword = passwordEncoder.encode(password);  // 비밀번호 암호화

                user.setPassword(encodedPassword);
                int createCnt = userService.createUser(user);

                if(createCnt == 1){ // 계정이 생성된 경우
                    User createdUser = userService.selectUserByUsername(username);
                    apiResponse.setResult(ApiBasicResult.INF_SUCCESS);
                    apiResponse.setData(createdUser);
                }else{  // 계정 생성에 실패한 경우
                    apiResponse.setResult(ApiBasicResult.ERR_DB);
                }
            }else{  // 아이디가 중복된 경우
                apiResponse.setResult(ApiBasicResult.ERR_DUP_ENTRY);
            }
        }

        return ResponseEntity.ok(apiResponse);
    }
}