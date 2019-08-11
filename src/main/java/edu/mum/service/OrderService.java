package edu.mum.service;

import edu.mum.domain.Buyer;
import edu.mum.domain.Orders;

import java.util.List;

public interface OrderService {
    public Orders getOrderByOrderId(Long id);
    public Orders saveOrder(Buyer buyer, Orders order);
    public void completeOrder(Orders order);
    public void cancelOrder(Orders order);
}
