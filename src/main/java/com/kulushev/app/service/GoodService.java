package com.kulushev.app.service;

import com.kulushev.app.dto.GoodReqDto;
import com.kulushev.app.entity.GoodEntity;
import com.kulushev.app.entity.OrderEntity;
import com.kulushev.app.repository.GoodRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GoodService {

    private final GoodRepository goodRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public List<GoodEntity> saveGoods(List<GoodReqDto> goods, Long orderId) {
        List<GoodEntity> goodEntities = goods.stream()
                .map(good -> {
                    GoodEntity goodEntity = new GoodEntity();
                    goodEntity.setName(good.name());
                    goodEntity.setPrice(good.price());
                    goodEntity.setOrder(entityManager.getReference(OrderEntity.class, orderId));
                    return goodEntity;
                })
                .toList();
        return goodRepository.saveAll(goodEntities);
    }
}
