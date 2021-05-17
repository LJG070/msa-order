package com.ljg.msaorder.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RequestOrder {
    private String productId;
    private Integer quantity;
    private Integer unitPrice;
}
