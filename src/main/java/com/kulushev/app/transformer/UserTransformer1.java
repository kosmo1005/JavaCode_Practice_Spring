package com.kulushev.app.transformer;

import com.kulushev.app.dto.GoodRespDto;
import com.kulushev.app.dto.OrderRespDto;
import com.kulushev.app.dto.UserReqDto;
import com.kulushev.app.dto.UserRespDto;
import com.kulushev.app.entity.GoodEntity;
import com.kulushev.app.entity.OrderEntity;
import com.kulushev.app.entity.UserEntity;
import com.kulushev.app.enums.OrderStatus;
import com.kulushev.app.enums.Role;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserTransformer1 {

    public UserRespDto entityToDto(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }

        String id = null;
        String login = null;
        String email = null;
        Role role = null;
        String name = null;
        List<OrderRespDto> orders = null;

        id = userEntity.getId();
        login = userEntity.getLogin();
        email = userEntity.getEmail();
        role = userEntity.getRole();
        name = userEntity.getName();
        orders = orderEntityListToOrderRespDtoList(userEntity);

        UserRespDto userRespDto = new UserRespDto( id, login, email, role, name, orders );

        return userRespDto;
    }


    public UserEntity dtoToEntity(UserReqDto userReqDto) {
        if ( userReqDto == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setName( userReqDto.name() );
        userEntity.setLogin( userReqDto.login() );
        userEntity.setEmail( userReqDto.email() );

        return userEntity;
    }

    protected GoodRespDto goodEntityToGoodRespDto(GoodEntity goodEntity, OrderEntity orderEntity) {
        if ( goodEntity == null ) {
            return null;
        }

        Long id = null;
        Long orderId = null;
        String name = null;
        BigDecimal price = null;

        id = goodEntity.getId();
        orderId = orderEntity.getId();
        name = goodEntity.getName();
        price = goodEntity.getPrice();



        GoodRespDto goodRespDto = new GoodRespDto( id, orderId, name, price );

        return goodRespDto;
    }

    protected List<GoodRespDto> goodEntityListToGoodRespDtoList(OrderEntity orderEntity) {
        if ( orderEntity.getGoods() == null ) {
            return null;
        }

        List<GoodRespDto> list1 = new ArrayList<GoodRespDto>( orderEntity.getGoods().size() );
        for ( GoodEntity goodEntity : orderEntity.getGoods() ) {
            list1.add( goodEntityToGoodRespDto( goodEntity, orderEntity ) );
        }

        return list1;
    }

    protected OrderRespDto orderEntityToOrderRespDto(OrderEntity orderEntity, UserEntity userEntity) {
        if ( orderEntity == null ) {
            return null;
        }

        Long id = null;
        String userId = null;
        OrderStatus status = null;
        BigDecimal totalPrice = null;
        List<GoodRespDto> goods = null;

        id = orderEntity.getId();
        userId = userEntity.getId();
        status = orderEntity.getStatus();
        totalPrice = orderEntity.getTotalPrice();
        goods = goodEntityListToGoodRespDtoList(orderEntity);



        OrderRespDto orderRespDto = new OrderRespDto( id, userId, status, totalPrice, goods );

        return orderRespDto;
    }

    protected List<OrderRespDto> orderEntityListToOrderRespDtoList(UserEntity userEntity) {

        if ( userEntity.getOrders() == null ) {
            return null;
        }

        List<OrderRespDto> list1 = new ArrayList<OrderRespDto>( userEntity.getOrders().size() );
        for ( OrderEntity orderEntity : userEntity.getOrders() ) {
            list1.add( orderEntityToOrderRespDto(orderEntity, userEntity) );
        }

        return list1;
    }
}
