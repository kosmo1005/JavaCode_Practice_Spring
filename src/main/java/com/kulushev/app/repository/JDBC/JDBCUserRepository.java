package com.kulushev.app.repository.JDBC;

import com.kulushev.app.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class JDBCUserRepository {

    private final JdbcTemplate jdbcTemplate;
    private final JDBCOrderRepository orderRepository;

    private final RowMapper<UserEntity> userRowMapper = (rs, rowNum) -> new UserEntity(
            UUID.fromString(rs.getString("id")),
            rs.getString("name"),
            rs.getString("email"),
            null
    );

    public List<UserEntity> findAll() {
        List<UserEntity> entities = jdbcTemplate.query("select * from my_user", userRowMapper);

        for (UserEntity user : entities) {
            user.setOrders(orderRepository.getOrdersByUserId(user.getId()));
        }
        return entities;
    }

    public Optional<UserEntity> findById(UUID id) {
        Optional<UserEntity> user = jdbcTemplate.query("SELECT * FROM my_user WHERE id = ?", userRowMapper, id)
                .stream()
                .findFirst();

        user.ifPresent(u -> u.setOrders(orderRepository.getOrdersByUserId(u.getId())));
        return user;
    }

    public Optional<UserEntity> findByEmail(String email) {
        Optional<UserEntity> user = jdbcTemplate.query("SELECT * FROM my_user WHERE email = ?", userRowMapper, email)
                .stream()
                .findFirst();

        user.ifPresent(u -> u.setOrders(orderRepository.getOrdersByUserId(u.getId())));
        return user;
    }

    public Optional<UserEntity> findByName(String name) {
        Optional<UserEntity> user = jdbcTemplate.query("SELECT * FROM my_user WHERE name = ?", userRowMapper, name)
                .stream()
                .findFirst();

        user.ifPresent(u -> u.setOrders(orderRepository.getOrdersByUserId(u.getId())));
        return user;
    }

    public UserEntity save(UserEntity user) {

        if (user.getId() == null) {
            user.setId(UUID.randomUUID());
        }

        jdbcTemplate.update(
                "INSERT INTO my_user (id, name, email) VALUES (?, ?, ?)",
                user.getId(), user.getName(), user.getEmail()
        );
        return user;
    }

    public void deleteById(UUID id) {
        jdbcTemplate.update("DELETE FROM my_user WHERE id = ?", id.toString());
    }

}
