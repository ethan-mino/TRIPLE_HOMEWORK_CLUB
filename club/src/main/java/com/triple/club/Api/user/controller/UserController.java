package com.triple.club.Api.user.controller;

import com.triple.club.Api.Util.ApiResponse;
import com.triple.club.Api.Util.ApiResult.ApiBasicResult;
import com.triple.club.Api.user.service.UserService;
import com.triple.club.Api.user.vo.User;
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

    public UserController(UserService userService, PasswordEncoder passwordEncoder){
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    private Boolean usernameDupValidation(String username){    // 아이디 중복 검사
        User user = userService.selectUserByUsername(username);
        return !(user == null);
    }

    @PostMapping("/signup") // 회원가입
    public ResponseEntity<ApiResponse<User>> signup(@Valid @RequestBody User user, BindingResult bindingResult){
        ApiResponse<User> apiResponse = new ApiResponse<>();

        if(bindingResult.hasErrors()){
            apiResponse.setResult(ApiBasicResult.ERR_INVALID_VALUE);
        }else{
            String username = user.getUsername();
            Boolean isUsernameDup = usernameDupValidation(username); // 아이디 중복 검사


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
