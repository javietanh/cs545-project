package edu.mum.domain.view;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SellerInfo {
    private Long id;
    private String name;
    private String description;
    private String phone;
    private String email;
    private String address;
}
