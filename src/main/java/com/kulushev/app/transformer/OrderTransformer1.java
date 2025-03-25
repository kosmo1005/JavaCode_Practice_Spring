package com.kulushev.app.transformer;

import com.kulushev.app.dto.GoodReqDto;
import com.kulushev.app.dto.GoodRespDto;
import com.kulushev.app.dto.OrderReqDto;
import com.kulushev.app.dto.OrderRespDto;
import com.kulushev.app.entity.GoodEntity;
import com.kulushev.app.entity.OrderEntity;
import com.kulushev.app.entity.UserEntity;
import com.kulushev.app.enums.OrderStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class OrderTransformer1 {
    public OrderRespDto entityToDto(OrderEntity orderEntity) {
        if ( orderEntity == null ) {
            return null;
        }

        String userId = null;
        Long id = null;
        OrderStatus status = null;
        BigDecimal totalPrice = null;
        List<GoodRespDto> goods = null;

        userId = orderEntityUserId( orderEntity );
        id = orderEntity.getId();
        status = orderEntity.getStatus();
        totalPrice = orderEntity.getTotalPrice();
        goods = goodEntityListToGoodRespDtoList( orderEntity.getGoods() );

        return new OrderRespDto( id, userId, status, totalPrice, goods );
    }

    public OrderEntity dtoToEntity(OrderReqDto orderReqDto) {
        if ( orderReqDto == null ) {
            return null;
        }

        OrderEntity orderEntity = new OrderEntity();

        orderEntity.setGoods( goodReqDtoListToGoodEntityList( orderReqDto.goods() ) );

        return orderEntity;
    }

    private String orderEntityUserId(OrderEntity orderEntity) {
        if ( orderEntity == null ) {
            return null;
        }
        UserEntity user = orderEntity.getUser();
        if ( user == null ) {
            return null;
        }
        String id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected GoodRespDto goodEntityToGoodRespDto(GoodEntity goodEntity) {
        if ( goodEntity == null ) {
            return null;
        }

        Long id = null;
        Long orderId = null;
        String name = null;
        BigDecimal price = null;

        id = goodEntity.getId();
        orderId = goodEntity.getOrder().getId();
        name = goodEntity.getName();
        price = goodEntity.getPrice();


        return new GoodRespDto( id, orderId, name, price );
    }

    protected List<GoodRespDto> goodEntityListToGoodRespDtoList(List<GoodEntity> list) {
        if ( list == null ) {
            return null;
        }

        List<GoodRespDto> list1 = new ArrayList<GoodRespDto>( list.size() );
        for ( GoodEntity goodEntity : list ) {
            list1.add( goodEntityToGoodRespDto( goodEntity ) );
        }

        return list1;
    }

    protected GoodEntity goodReqDtoToGoodEntity(GoodReqDto goodReqDto) {
        if ( goodReqDto == null ) {
            return null;
        }

        GoodEntity goodEntity = new GoodEntity();

        goodEntity.setName( goodReqDto.name() );
        goodEntity.setPrice( goodReqDto.price() );

        return goodEntity;
    }

    protected List<GoodEntity> goodReqDtoListToGoodEntityList(List<GoodReqDto> list) {
        if ( list == null ) {
            return null;
        }

        List<GoodEntity> list1 = new ArrayList<GoodEntity>( list.size() );
        for ( GoodReqDto goodReqDto : list ) {
            list1.add( goodReqDtoToGoodEntity( goodReqDto ) );
        }

        return list1;
    }
}
