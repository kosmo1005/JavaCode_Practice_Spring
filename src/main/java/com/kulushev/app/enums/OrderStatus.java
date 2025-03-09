package com.kulushev.app.enums;


/**
 * Новый заказ (NEW) – Заказ только что был оформлен, но еще не принят в обработку.
 * Ожидает оплаты (AWAITING_PAYMENT) – Заказ ожидает поступления оплаты.
 * Оплачен (PAID) – Заказ успешно оплачен.
 * Подготовка к отправке (PREPARING_FOR_SHIPMENT) – Заказ собран и готовится к отправке.
 * Отправлен (SHIPPED) – Заказ отправлен клиенту.
 * В пути (IN_TRANSIT) – Заказ находится в процессе доставки.
 * Доставлен (DELIVERED) – Заказ был доставлен покупателю.
 * Завершен (COMPLETED) – Заказ завершен, все этапы выполнены.
 * Отменен (CANCELLED) – Заказ отменен по желанию клиента или по внутренним причинам магазина.
 * Возвращен (RETURNED) – Заказ или часть товара возвращены клиентом
 */

public enum OrderStatus {
    NEW,
    AWAITING_PAYMENT,
    PAID,
    PREPARING_FOR_SHIPMENT,
    SHIPPED,
    IN_TRANSIT,
    DELIVERED,
    COMPLETED,
    CANCELLED,
    RETURNED
}
