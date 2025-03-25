package com.kulushev.app.service;

import com.kulushev.app.dto.GoodReqDto;
import com.kulushev.app.dto.OrderReqDto;
import com.kulushev.app.dto.OrderRespDto;
import com.kulushev.app.entity.GoodEntity;
import com.kulushev.app.entity.OrderEntity;
import com.kulushev.app.entity.UserEntity;
import com.kulushev.app.enums.OrderStatus;
import com.kulushev.app.exception.notFound.OrderNotFoundException;
import com.kulushev.app.exception.notFound.UserNotFoundException;
import com.kulushev.app.repository.OrderRepository;
import com.kulushev.app.transformer.OrderTransformer1;
import com.kulushev.app.util.CheckNPE;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository repo;
    private final OrderTransformer1 t;
    private final UserService userService;
    private final GoodService goodService;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public OrderRespDto createOrder(OrderReqDto dto) {
        CheckNPE.checkNPE(dto);

        if(!userService.userExistsById(dto.userId())){
            throw new UserNotFoundException("User not found");
        }

        var entity = new OrderEntity();
        entity.setUser(entityManager.getReference(UserEntity.class, dto.userId()));
        entity.setStatus(OrderStatus.NEW);
        entity.setTotalPrice(
                dto.goods().stream()
                        .map(GoodReqDto::price)
                        .reduce(BigDecimal.ZERO, BigDecimal::add));

        OrderEntity savedOrder = repo.save(entity);
        List<GoodEntity> goodsWithIds = goodService.saveGoods(dto.goods(), savedOrder.getId());
        savedOrder.setGoods(goodsWithIds);
        return t.entityToDto(savedOrder);
    }

    @Transactional
    public OrderRespDto updateOrder(Long id, OrderReqDto dto) {
        if (!orderExists(id)) {
            throw new OrderNotFoundException("Order not found");
        }
        if (!userService.userExistsById(dto.userId())) {
            throw new UserNotFoundException("User not found");
        }

        var order = t.dtoToEntity(dto);
        order.setId(id);
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
            throw new UserNotFoundException("Order not found");
        }
        repo.deleteById(id);
    }

    @Transactional
    public boolean orderExists(Long id) {
        return repo.findById(id).isPresent();
    }
}
