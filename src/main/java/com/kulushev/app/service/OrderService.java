package com.kulushev.app.service;

import com.kulushev.app.dto.OrderReqDto;
import com.kulushev.app.dto.OrderRespDto;
import com.kulushev.app.entity.GoodEntity;
import com.kulushev.app.entity.OrderEntity;
import com.kulushev.app.entity.UserEntity;
import com.kulushev.app.enums.OrderStatus;
import com.kulushev.app.exception.UserNotFoundException;
import com.kulushev.app.repository.OrderRepository;
import com.kulushev.app.transformer.OrderTransformer;
import com.kulushev.app.util.CheckNPE;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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

        if(!userService.userExists(dto.userId())){
            throw new UserNotFoundException("User not found");
        }

        var entity = t.dtoToEntity(dto);
        entity.setUser(entityManager.getReference(UserEntity.class, dto.userId()));
        entity.setStatus(OrderStatus.NEW);
        entity.setTotalPrice(
                entity.getGoods().stream()
                        .map(GoodEntity::getPrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add));

        OrderEntity savedOrder = repo.save(entity);
        return t.entityToDto(savedOrder);
    }

    @Transactional
    public void deleteOrderById(Long id) {
        if (repo.findById(id).isEmpty()) {
            throw new UserNotFoundException("Order not found");
        }
        repo.deleteById(id);
    }
}
