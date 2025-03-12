package com.kulushev.app.service;

import com.kulushev.app.dto.GoodReqDto;
import com.kulushev.app.dto.OrderReqDto;
import com.kulushev.app.dto.OrderRespDto;
import com.kulushev.app.entity.GoodEntity;
import com.kulushev.app.entity.OrderEntity;
import com.kulushev.app.enums.OrderStatus;
import com.kulushev.app.exception.OrderNotFoundException;
import com.kulushev.app.exception.UserNotFoundException;
import com.kulushev.app.repository.JDBC.JDBCOrderRepository;
import com.kulushev.app.transformer.OrderTransformer;
import com.kulushev.app.util.CheckNPE;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final JDBCOrderRepository repo;
    private final OrderTransformer t;
    private final UserService userService;

    @Transactional
    //TODO: решить проблему идемпотентности (ключ идемпотентности)
    public OrderRespDto createOrder(OrderReqDto dto) {
        CheckNPE.checkNPE(dto);

        if(!userService.userExists(UUID.fromString(dto.userId()))){
            throw new UserNotFoundException("User not found");
        }

        var entity = t.dtoToEntity(dto);
        entity.setStatus(OrderStatus.NEW);
        entity.setTotalPrice(
                entity.getGoods().stream()
                        .map(GoodEntity::getPrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add));

        var savedEntity = repo.save(entity);
        return t.entityToDto(savedEntity);
    }

    @Transactional(readOnly = true)
    public OrderRespDto getById(UUID id) {
        var order = repo.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));
        return t.entityToDto(order);
    }

    @Transactional(readOnly = true)
    public List<OrderRespDto> getAll() {
        return repo.findAll().stream()
                .map(t::entityToDto)
                .toList();
    }

    @Transactional
    public OrderRespDto updateOrder(UUID id, OrderReqDto dto) {
        if (!orderExists(id)) {
            throw new OrderNotFoundException("Order not found");
        }
        if (!userService.userExists(UUID.fromString(dto.userId()))) {
            throw new UserNotFoundException("User not found");
        }

        var order = t.dtoToEntity(dto);
        order.setId(id);
        order.setTotalPrice(
                dto.goods().stream()
                        .map(GoodReqDto::price)
                        .reduce(BigDecimal.ZERO, BigDecimal::add));

        OrderEntity updatedOrder = repo.update(order);
        return t.entityToDto(updatedOrder);
    }

    @Transactional
    public void deleteOrderById(UUID id) {
        if (repo.findById(id).isEmpty()) {
            throw new OrderNotFoundException("Order not found");
        }
        repo.deleteById(id);
    }

    @Transactional
    public boolean orderExists(UUID id) {
        return repo.findById(id).isPresent();
    }
}
