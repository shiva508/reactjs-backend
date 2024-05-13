package com.comrade.reactjsbackend.controller;

import com.comrade.reactjsbackend.entity.UserEntity;
import com.comrade.reactjsbackend.model.AuthUser;
import com.comrade.reactjsbackend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/save")
    public UserEntity save(@RequestBody UserEntity userEntity){
        return userService.save(userEntity);
    }
    @GetMapping("/all")
    public List<UserEntity> all(){
        return userService.all();
    }

    @GetMapping("/authAll")
    public List<AuthUser> authUsers(){
        return userService.authUsers();
    }

    @GetMapping("/email/{email}")
    public UserEntity findByEmail(@PathVariable("email") String email){
        return userService.findByEmail(email);
    }
}
