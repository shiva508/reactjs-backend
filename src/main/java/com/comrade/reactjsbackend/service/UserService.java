package com.comrade.reactjsbackend.service;

import com.comrade.reactjsbackend.entity.UserEntity;
import com.comrade.reactjsbackend.model.AuthUser;

import java.util.List;

public interface UserService {

    UserEntity save(UserEntity userEntity);
    List<UserEntity> all();
    List<AuthUser> authUsers();
    UserEntity findByEmail(String email);

}
