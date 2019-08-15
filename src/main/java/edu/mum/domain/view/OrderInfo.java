package edu.mum.domain.view;

import edu.mum.domain.Buyer;
import edu.mum.domain.OrderItem;
import edu.mum.domain.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderInfo {
    private Long id;
    private List<OrderItem> orderItems = new ArrayList<OrderItem>();
    private BigDecimal totalAmount = new BigDecimal(0.00);
    private Buyer buyer;
    private String shippingAddress;
    private String billingAddress;
    private String paymentMethod;
    private String paymentInfo;
    private OrderStatus status = OrderStatus.NEW;
    private LocalDateTime orderedDate;
    private LocalDateTime endDate;
    private Boolean usingPoints = false;
}
