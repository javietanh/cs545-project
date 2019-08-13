package edu.mum.domain.view;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartInfo {
    private Long id;
    private String productName;
    private BigDecimal productPrice;
    private int quantity;
}
