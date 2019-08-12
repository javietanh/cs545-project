package edu.mum.service;

import edu.mum.domain.Buyer;
import edu.mum.domain.Orders;

import java.util.List;

public interface OrderService {
    Orders getOrderByOrderId(Long id);
    Orders saveOrder(Buyer buyer, Orders order);
    void completeOrder(Orders order);
    void cancelOrder(Orders order);
}
