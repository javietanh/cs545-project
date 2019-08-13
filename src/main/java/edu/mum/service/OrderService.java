package edu.mum.service;

import edu.mum.domain.Buyer;
import edu.mum.domain.Order;
import edu.mum.domain.OrderItem;
import edu.mum.domain.Orders;
import edu.mum.domain.view.OrderItemInfo;
import java.io.File;
import java.util.List;

public interface OrderService {
    Order getOrderById(Long id);
    Order saveOrder(Buyer buyer, Order order);
    void completeOrder(Order order);
    void cancelOrder(Order order);
    File downloadReceipt(Order order) throws Exception;
    Orders getOrderById(Long id);
    Orders saveOrder(Buyer buyer, Orders order);
    OrderItem saveOrderItem(OrderItem orderItem);
    OrderItem updateOrderItem(OrderItem orderItem);
    void completeOrder(Orders order);
    void cancelOrder(Orders order);
    File downloadReceipt(Orders order) throws Exception;
    OrderItem getOrderItemById(Long itemId);
    List<OrderItem> getOrderItemsBySeller(Long sellerId);
}
