package com.kulushev.app.controllerTest.dataProvider;

import com.kulushev.app.dto.GoodRespDto;
import com.kulushev.app.dto.OrderRespDto;
import com.kulushev.app.enums.OrderStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class OrderDataProvider {
    public static final UUID USER_ID = UUID.fromString("32e2a465-4945-4f5e-81df-63e1780df364");


    public static List<OrderRespDto> getOrdersList() {
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
                        getGoodsList_2()),
                new OrderRespDto(
                        3L,
                        USER_ID,
                        OrderStatus.NEW,
                        BigDecimal.valueOf(2.00),
                        getGoodsList_0()),
                new OrderRespDto(
                        4L,
                        USER_ID,
                        OrderStatus.NEW,
                        BigDecimal.valueOf(2.00),
                        getGoodsList_0()),
                new OrderRespDto(
                        5L,
                        USER_ID,
                        OrderStatus.NEW,
                        BigDecimal.valueOf(2.00),
                        getGoodsList_0()),
                new OrderRespDto(
                        6L,
                        USER_ID,
                        OrderStatus.NEW,
                        BigDecimal.valueOf(2.00),
                        getGoodsList_0()),
                new OrderRespDto(
                        7L,
                        USER_ID,
                        OrderStatus.NEW,
                        BigDecimal.valueOf(2.00),
                        getGoodsList_0()),
                new OrderRespDto(
                        8L,
                        USER_ID,
                        OrderStatus.NEW,
                        BigDecimal.valueOf(2.00),
                        getGoodsList_0()),
                new OrderRespDto(
                        9L,
                        USER_ID,
                        OrderStatus.NEW,
                        BigDecimal.valueOf(2.00),
                        getGoodsList_0()),
                new OrderRespDto(
                        10L,
                        USER_ID,
                        OrderStatus.NEW,
                        BigDecimal.valueOf(2.00),
                        getGoodsList_0()),
                new OrderRespDto(
                        11L,
                        USER_ID,
                        OrderStatus.NEW,
                        BigDecimal.valueOf(2.00),
                        getGoodsList_0()));
    }

    public static List<GoodRespDto> getGoodsList_1() {
        return List.of(
                new GoodRespDto(1L, 1L, "Laptop", BigDecimal.valueOf(1000.00)),
                new GoodRespDto(2L, 1L, "Mouse", BigDecimal.valueOf(100.00)));
    }

    public static List<GoodRespDto> getGoodsList_2() {
        return List.of(
                new GoodRespDto(3L, 2L, "Laptop", BigDecimal.valueOf(2000.00)),
                new GoodRespDto(4L, 2L, "Mouse", BigDecimal.valueOf(200.00)));
    }

    public static List<GoodRespDto> getGoodsList_0() {
        return List.of(
                new GoodRespDto(0L, 0L, "L", BigDecimal.valueOf(1.00)),
                new GoodRespDto(0L, 0L, "M", BigDecimal.valueOf(1.00)));
    }
}
