package com.kulushev.app.repository.JDBC;

import com.kulushev.app.entity.GoodEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class JDBCGoodRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<GoodEntity> goodRowMapper = (rs, rowNum) -> new GoodEntity(
            UUID.fromString(rs.getString("id")),
            rs.getString("name"),
            rs.getBigDecimal("price"),
            UUID.fromString(rs.getString("order_id"))
    );

    public List<GoodEntity> getGoodsByOrderId(UUID orderId) {
        String sql = "SELECT * FROM good WHERE order_id = ?";
        return jdbcTemplate.query(sql, goodRowMapper, orderId);
    }

    public void save(GoodEntity good, UUID orderId) {
        if (good.getId() == null) {
            good.setId(UUID.randomUUID());
        }
        jdbcTemplate.update(
                "INSERT INTO good (id, name, price, order_id) VALUES (?, ?, ?, ?)",
                good.getId(),
                good.getName(),
                good.getPrice(),
                orderId
        );
    }
}
