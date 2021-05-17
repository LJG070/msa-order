package com.ljg.msaorder.service;

import com.ljg.msaorder.dto.OrderDto;
import com.ljg.msaorder.entity.OrderEntity;

public interface OrderService {
    OrderDto createOrder(OrderDto order);
    OrderDto getOrderByOrderId(String orderId);
    Iterable<OrderEntity> getAllOrdersByUserId(String userId);
}
