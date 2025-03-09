package com.kulushev.app.util;

import java.lang.reflect.Field;

public class CheckNPE {
    public static void checkNPE(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Переданный объект является null");
        }
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);

            try {
                Object value = field.get(obj);
                if (value == null && !field.getType().isPrimitive()) {
                    throw new IllegalArgumentException("Поле '" + field.getName() + "' не должно быть null");
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Ошибка доступа к полю: " + field.getName(), e);
            }
        }
    }
}
