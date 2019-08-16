package edu.mum.service;

import edu.mum.domain.OrderItem;

import java.util.List;

public interface OrderItemService {
    OrderItem saveOrderItem(OrderItem orderItem);

    List<OrderItem> getOrderItems();

    List<OrderItem> getOrderItemsWithNotNullReviews();

    OrderItem getOrderItemById(Long id);

    List<String> getApprovedReviews(Long itemId);

    void deleteOrderItem(Long id);
}
