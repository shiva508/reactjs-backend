package com.comrade.reactjsbackend.repository;

import com.comrade.reactjsbackend.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity,Long> {
}
