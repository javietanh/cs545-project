package edu.mum.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Order {
    @Id
    @GeneratedValue
    private Long id;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<OrderItem>();
    private BigDecimal totalAmount = new BigDecimal(0.00);
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "buyer_id")
    private Buyer buyer;
    private String shippingAddress;
    private String billingAddress;
    private String paymentMethod;
    private String paymentInfo;
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.NEW;
    private LocalDateTime orderedDate;
    private LocalDateTime endDate;

    public void addOrderItem(OrderItem item) {
        orderItems.add(item);
    }
}
