package com.kulushev.app.service;

import com.kulushev.app.dto.GoodReqDto;
import com.kulushev.app.dto.OrderReqDto;
import com.kulushev.app.dto.OrderRespDto;
import com.kulushev.app.entity.GoodEntity;
import com.kulushev.app.entity.OrderEntity;
import com.kulushev.app.entity.UserEntity;
import com.kulushev.app.enums.OrderStatus;
import com.kulushev.app.exception.OrderNotFoundException;
import com.kulushev.app.exception.UserNotFoundException;
import com.kulushev.app.repository.OrderRepository;
import com.kulushev.app.transformer.OrderTransformer;
import com.kulushev.app.util.CheckNPE;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository repo;
    private final OrderTransformer t;
    private final UserService userService;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    //TODO: решить проблему идемпотентности (ключ идемпотентности)
    public OrderRespDto createOrder(OrderReqDto dto) {
        CheckNPE.checkNPE(dto);

        if(!userService.userExists(UUID.fromString(dto.userId()))){
            throw new UserNotFoundException("User not found");
        }

        var entity = t.dtoToEntity(dto);
        entity.setUser(entityManager.getReference(UserEntity.class, UUID.fromString(dto.userId())));
        entity.setStatus(OrderStatus.NEW);
        entity.setTotalPrice(
                entity.getGoods().stream()
                        .map(GoodEntity::getPrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add));

        var savedOrder = repo.save(entity);
        return t.entityToDto(savedOrder);
    }

    @Transactional(readOnly = true)
    public OrderRespDto getById(Long id) {
        var order = repo.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));
        return t.entityToDto(order);
    }

    @Transactional(readOnly = true)
    public Page<OrderRespDto> getAll(Pageable pageable) {
        return repo.findAll(pageable).map(t::entityToDto);
    }

    @Transactional
    public OrderRespDto updateOrder(Long id, OrderReqDto dto) {
        if (!orderExists(id)) {
            throw new OrderNotFoundException("Order not found");
        }
        if (!userService.userExists(UUID.fromString(dto.userId()))) {
            throw new UserNotFoundException("User not found");
        }

        var order = t.dtoToEntity(dto);
        order.setUser(entityManager.getReference(UserEntity.class, dto.userId()));
        order.setStatus(dto.status());
        order.setTotalPrice(
                dto.goods().stream()
                        .map(GoodReqDto::price)
                        .reduce(BigDecimal.ZERO, BigDecimal::add));

        OrderEntity updatedOrder = repo.save(order);
        return t.entityToDto(updatedOrder);
    }

    @Transactional
    public void deleteOrderById(Long id) {
        if (repo.findById(id).isEmpty()) {
            throw new OrderNotFoundException("Order not found");
        }
        repo.deleteById(id);
    }

    @Transactional
    public boolean orderExists(Long id) {
        return repo.findById(id).isPresent();
    }
}
