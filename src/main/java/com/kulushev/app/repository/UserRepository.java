package com.kulushev.app.repository;

import com.kulushev.app.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByName(String username);

    Optional<UserEntity> findByEmail(String email);


}