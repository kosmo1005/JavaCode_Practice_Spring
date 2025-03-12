package com.kulushev.app.repository.JDBC;

import com.kulushev.app.entity.GoodEntity;
import com.kulushev.app.entity.OrderEntity;
import com.kulushev.app.enums.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class JDBCOrderRepository {

    private final JdbcTemplate jdbcTemplate;
    private final JDBCGoodRepository goodRepository;

    private final RowMapper<OrderEntity> orderRowMapper = (rs, rowNum) -> new OrderEntity(
            UUID.fromString(rs.getString("id")),
            UUID.fromString(rs.getString("user_id")),
            OrderStatus.valueOf(rs.getString("status")),
            rs.getBigDecimal("total_price"),
            List.of()
    );

    public List<OrderEntity> findAll() {
        List<OrderEntity> entities = jdbcTemplate.query("select * from user_order", orderRowMapper);

        for (OrderEntity order : entities) {
            order.setGoods(goodRepository.getGoodsByOrderId(order.getId()));
        }
        return entities;
    }

    public Optional<OrderEntity> findById(UUID id) {
        Optional<OrderEntity> order = jdbcTemplate.query("SELECT * FROM user_order WHERE id = ?", orderRowMapper, id)
                .stream()
                .findFirst();

        order.ifPresent(o -> o.setGoods(goodRepository.getGoodsByOrderId(o.getId())));
        return order;
    }

    public OrderEntity save(OrderEntity order) {

        if (order.getId() == null) {
            order.setId(UUID.randomUUID());
        }
        jdbcTemplate.update(
                "INSERT INTO user_order (id, user_id, status, total_price) VALUES (?, ?, ?, ?)",
                order.getId(),
                order.getUserId(),
                order.getStatus().toString(),
                order.getTotalPrice()
        );
        if (order.getGoods() != null && !order.getGoods().isEmpty()) {
            for (GoodEntity good : order.getGoods()) {
                good.setOrderId(order.getId());
                goodRepository.save(good,order.getId());
            }
        }

        return order;
    }

    public OrderEntity update(OrderEntity order) {
        jdbcTemplate.update(
                "UPDATE user_order SET user_id = ?, status = ?, total_price = ? WHERE id = ?",
                order.getUserId(),
                order.getStatus().toString(),
                order.getTotalPrice(),
                order.getId()
        );

        jdbcTemplate.update("DELETE FROM good WHERE order_id = ?", order.getId());

        if (order.getGoods() != null && !order.getGoods().isEmpty()) {
            for (GoodEntity good : order.getGoods()) {
                goodRepository.save(good, order.getId());
            }
        }
        return order;
    }

    public void deleteById(UUID id) {
        jdbcTemplate.update("DELETE FROM user_order WHERE id = ?", id);
    }

    public List<OrderEntity> getOrdersByUserId(UUID userId) {
        String sql = "SELECT * FROM user_order WHERE user_id = ?";
        List<OrderEntity> orders = jdbcTemplate.query(sql, orderRowMapper, userId);
        orders.forEach(order -> order.setGoods(goodRepository.getGoodsByOrderId(order.getId())));
        return orders;
    }
}
