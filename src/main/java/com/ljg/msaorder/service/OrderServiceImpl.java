package com.ljg.msaorder.service;

import com.ljg.msaorder.dto.OrderDto;
import com.ljg.msaorder.entity.OrderEntity;
import com.ljg.msaorder.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;

    @Override
    public OrderDto createOrder(OrderDto order) {
        order.setOrderId(UUID.randomUUID().toString());
        order.setTotalPrice(order.getUnitPrice() * order.getQuantity());

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        OrderEntity orderEntity = mapper.map(order, OrderEntity.class);

        OrderEntity result = orderRepository.save(orderEntity);

        return mapper.map(result, OrderDto.class);
    }

    @Override
    public OrderDto getOrderByOrderId(String orderId) {
        OrderEntity order = orderRepository.findByOrderId(orderId).orElseThrow(EntityNotFoundException::new);
        OrderDto orderDto = new ModelMapper().map(order, OrderDto.class);
        return orderDto;
    }

    @Override
    public Iterable<OrderEntity> getAllOrdersByUserId(String userId) {
        return orderRepository.findByUserId(userId);

    }
}
