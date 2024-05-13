package com.comrade.reactjsbackend.repository;

import com.comrade.reactjsbackend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {

    @Query("select u from UserEntity u left join fetch u.roles")
    List<UserEntity> findAllUsers();
    Optional<UserEntity> findByEmail(String email);

}
