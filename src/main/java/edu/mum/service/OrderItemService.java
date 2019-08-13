package edu.mum.service;

import edu.mum.domain.OrderItem;

import java.util.List;

public interface OrderItemService {
    public OrderItem saveOrderItem(OrderItem orderItem);

    public List<OrderItem> getOrderItems();

    public OrderItem getOrderItemById(Long id);
}
