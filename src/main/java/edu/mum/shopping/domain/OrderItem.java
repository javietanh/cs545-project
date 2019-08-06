package edu.mum.shopping.domain;

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
    @JoinColumn(name="product_id")
    private Product product;
    private BigDecimal price;
    private int quantity;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "order_id")
    private Orders order;
    private String review;
    private Boolean reviewStatus;
    private int rating;
    private String orderStatus;
    private LocalDateTime shippingDate;
    private LocalDateTime deliveredDate;
}
