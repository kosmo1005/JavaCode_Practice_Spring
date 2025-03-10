package com.kulushev.app;

import com.kulushev.app.dto.GoodRespDto;
import com.kulushev.app.dto.OrderRespDto;
import com.kulushev.app.dto.UserReqDto;
import com.kulushev.app.dto.UserRespDto;
import com.kulushev.app.enums.Views;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonMapper {
    public static String listUserRespDto_ToJson(List<UserRespDto> users, Views view) {
        StringBuilder json = new StringBuilder("[");

        for (int i = 0; i < users.size(); i++) {
            UserRespDto user = users.get(i);
            json.append(oneUserToJson(user, view));
            if (i < users.size() - 1) {
                json.append(",");
            }
        }

        json.append("]");
        return json.toString();
    }

    public static String userRespDto_ToJson(UserRespDto user, Views view) {
        StringBuilder json = new StringBuilder("[");
        json.append(oneUserToJson(user, view));
        json.append("]");

        return json.toString();
    }

    private static String oneUserToJson(UserRespDto user, Views view) {
        StringBuilder json = new StringBuilder();
        switch (view) {
            case SHORT_INFO -> {
                json.append("{");
                json.append("\"name\":\"").append(user.name()).append("\",")
                        .append("\"email\":\"").append(user.email()).append("\",");
                json.append("}");
            }
            case FULL_INFO -> {
                json.append("{");
                json.append("\"id\":\"").append(user.id()).append("\",");
                json.append("\"name\":\"").append(user.name()).append("\",")
                        .append("\"email\":\"").append(user.email()).append("\",");
                if (user.orders() != null && !user.orders().isEmpty()) {
                    json.append("\"orders\":").append(getOrders_ToJson(user.orders()));
                }
                json.append("}");
            }

        }
        return json.toString();
    }

    private static String getOrders_ToJson(List<OrderRespDto> orders) {
        StringBuilder json = new StringBuilder("[");

        for (int i = 0; i < orders.size(); i++) {
            OrderRespDto order = orders.get(i);
            json.append("{")
                    .append("\"id\":").append(order.id()).append(",")
                    .append("\"userId\":\"").append(order.userId()).append("\",")
                    .append("\"status\":\"").append(order.status()).append("\",")
                    .append("\"totalPrice\":").append(order.totalPrice()).append(",")
                    .append("\"goods\":").append(getGoods_ToJson(order.goods()))
                    .append("}");

            if (i < orders.size() - 1) {
                json.append(",");
            }
        }

        json.append("]");
        return json.toString();
    }

    private static String getGoods_ToJson(List<GoodRespDto> goods) {
        StringBuilder json = new StringBuilder("[");

        for (int i = 0; i < goods.size(); i++) {
            GoodRespDto good = goods.get(i);
            json.append("{")
                    .append("\"id\":").append(good.id()).append(",")
                    .append("\"orderId\":").append(good.orderId()).append(",")
                    .append("\"name\":\"").append(good.name()).append("\",")
                    .append("\"price\":").append(good.price())
                    .append("}");

            if (i < goods.size() - 1) {
                json.append(",");
            }
        }

        json.append("]");
        return json.toString();
    }

    public static UserReqDto json_ToUserReqDto(String json) {
        String name = extractJsonValue(json, "name");
        String email = extractJsonValue(json, "email");
        return new UserReqDto(name, email);
    }

    private static String extractJsonValue(String json, String key) {
        String pattern = "\"\\s*" + key + "\\s*\"\\s*:\\s*\"([^\"]+)\"";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(json);
        return matcher.find() ? matcher.group(1) : null;
    }




}
