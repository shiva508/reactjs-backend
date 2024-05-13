package com.comrade.reactjsbackend.service;

import com.comrade.reactjsbackend.entity.RoleEntity;
import com.comrade.reactjsbackend.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService{

    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public RoleEntity save(RoleEntity roleEntity) {
        return roleRepository.save(roleEntity);
    }

    @Override
    @Transactional
    public List<RoleEntity> all() {
        return roleRepository.findAll();
    }
}
