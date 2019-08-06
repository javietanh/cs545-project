package edu.mum.domain;

import java.time.LocalDateTime;

public class Order {
    private Long id;
    private Long buyerId;
    private LocalDateTime orderDate;
    private LocalDateTime shippingDate;
    private String status;
    private int totalAmount;
}
