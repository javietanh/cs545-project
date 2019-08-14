package edu.mum.domain.view;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CartInfo {
    private Long id;
    private String productName;
    private BigDecimal productPrice;
    private String picture;
    private int quantity;
}
