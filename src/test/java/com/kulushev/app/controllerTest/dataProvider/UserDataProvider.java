package com.kulushev.app.controllerTest.dataProvider;

import com.kulushev.app.dto.GoodRespDto;
import com.kulushev.app.dto.OrderRespDto;
import com.kulushev.app.dto.UserReqDto;
import com.kulushev.app.dto.UserRespDto;
import com.kulushev.app.enums.OrderStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class UserDataProvider {
    public static final UUID USER_ID = UUID.fromString("32e2a465-4945-4f5e-81df-63e1780df364");

    public static UserRespDto getUserRespDto_1() {
        return new UserRespDto(
                USER_ID,
                "John Doe",
                "john@example.com",
                getOrdersList());
    }

    public static List<OrderRespDto> getOrdersList(){
        return List.of(
                new OrderRespDto(
                        1L,
                        USER_ID,
                        OrderStatus.NEW,
                        BigDecimal.valueOf(1100.00),
                        getGoodsList_1()),
                new OrderRespDto(
                        2L,
                        USER_ID,
                        OrderStatus.NEW,
                        BigDecimal.valueOf(2200.00),
                        getGoodsList_2()));
    }

    public static List<GoodRespDto> getGoodsList_1(){
        return List.of(
                new GoodRespDto(1L, 1L, "Laptop", BigDecimal.valueOf(1000.00)),
                new GoodRespDto(2L, 1L, "Mouse", BigDecimal.valueOf(100.00)));
    }

    public static List<GoodRespDto> getGoodsList_2(){
        return List.of(
                new GoodRespDto(3L, 2L, "Laptop", BigDecimal.valueOf(2000.00)),
                new GoodRespDto(4L, 2L, "Mouse", BigDecimal.valueOf(200.00)));
    }

    public static UserReqDto getUserReq_ValidDto() {
        return new UserReqDto(
                "John Doe",
                "john@example.com");
    }
    public static UserReqDto getUserReqDto_InvalidName() {
        return new UserReqDto(
                "Jo34 Doe",
                "john@example.com");
    }
    public static UserReqDto getUserReqDto_InvalidEmail() {
        return new UserReqDto(
                "John Doe",
                "johnexample.com");
    }
    public static UserReqDto getUserReqDto_InvalidEmailAndName() {
        return new UserReqDto(
                "Jo34 Doe",
                "johnexample.com");
    }
}
