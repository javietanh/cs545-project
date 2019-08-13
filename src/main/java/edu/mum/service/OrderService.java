package edu.mum.service;

import edu.mum.domain.Buyer;
import edu.mum.domain.OrderItem;
import edu.mum.domain.Orders;

import java.io.File;

public interface OrderService {
    Orders getOrderById(Long id);
    Orders saveOrder(Buyer buyer, Orders order);
    void completeOrder(Orders order);
    void cancelOrder(Orders order);
    File downloadReceipt(Orders order) throws Exception;
    OrderItem getOrderItemById(Long itemId);
}
