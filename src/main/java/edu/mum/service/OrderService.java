package edu.mum.service;

import edu.mum.domain.Buyer;
import edu.mum.domain.Order;

import java.util.List;

public interface OrderService {
    Order getOrderByOrderId(Long id);
    Order saveOrder(Buyer buyer, Order order);
    void completeOrder(Order order);
    void cancelOrder(Order order);
}
