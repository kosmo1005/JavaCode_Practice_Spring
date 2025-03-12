package com.kulushev.app.repository;

import com.kulushev.app.entity.UserEntity;
import com.kulushev.app.views.UserFullNameProjection;
import com.kulushev.app.views.UserWithInfoAboutOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByFirstName(String username);

    Optional<UserEntity> findByEmail(String email);

    Optional<UserFullNameProjection> findFullNameById(UUID id);

    @Query("""
        SELECT u FROM UserEntity u 
        LEFT JOIN u.orders o 
        LEFT JOIN o.goods g 
        WHERE u.id = :id
    """)
    Optional<UserWithInfoAboutOrders> findUserWithInfoAboutOrders(UUID id);
}