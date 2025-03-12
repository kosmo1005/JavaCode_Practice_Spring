package com.kulushev.app.views;

import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.util.Set;

public interface UserWithInfoAboutOrders {
    @Value("#{target.firstName + ' ' + target.lastName}")
    String getFullName();
    Set<OrderProjection> getOrders();

    interface OrderProjection {
        String getStatus();
        BigDecimal getTotalPrice();
        Set<GoodProjection> getGoods();

        interface GoodProjection{
            String getName();
        }
    }
}
