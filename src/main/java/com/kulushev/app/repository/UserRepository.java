package com.kulushev.app.repository;

import com.kulushev.app.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findByName(String username);

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByLogin(String login);

}