package com.comrade.reactjsbackend.service;

import com.comrade.reactjsbackend.entity.RoleEntity;

import java.util.List;

public interface RoleService {
    RoleEntity save(RoleEntity roleEntity);
    List<RoleEntity> all();
}
