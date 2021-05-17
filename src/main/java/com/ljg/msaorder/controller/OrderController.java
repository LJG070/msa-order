package com.ljg.msaorder.controller;

import com.ljg.msaorder.dto.OrderDto;
import com.ljg.msaorder.dto.RequestOrder;
import com.ljg.msaorder.dto.ResponseOrder;
import com.ljg.msaorder.entity.OrderEntity;
import com.ljg.msaorder.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/order-service")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/{userId}/orders")
    public ResponseEntity createOrder(@PathVariable("userId") String userId, @RequestBody RequestOrder order) {
        System.out.println(order);
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        OrderDto orderDto = mapper.map(order, OrderDto.class);
        orderDto.setUserId(userId);

        OrderDto createdOrder = orderService.createOrder(orderDto);

        ResponseOrder responseOrder = mapper.map(createdOrder, ResponseOrder.class);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseOrder);
    }

    @GetMapping("{userId}/orders")
    public ResponseEntity getOrder(@PathVariable("userId") String userId) {
        Iterable<OrderEntity> orderList = orderService.getAllOrdersByUserId(userId);
        List<ResponseOrder> result = new ArrayList<>();

        orderList.forEach(order -> {
            result.add(new ModelMapper().map(order, ResponseOrder.class));
        });

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }
}
