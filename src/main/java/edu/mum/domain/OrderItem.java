package edu.mum.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
public class OrderItem {
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private int quantity;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "order_id")
    private Order order;
    private String review;
    private Boolean reviewStatus;
    private int rating = 0;
    @Enumerated(EnumType.STRING)
    private OrderItemStatus orderStatus = OrderItemStatus.ORDERED;
    private LocalDateTime shippingDate;
    private LocalDateTime deliveredDate;
}
