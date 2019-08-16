package edu.mum.service.impl;

import edu.mum.domain.OrderItem;
import edu.mum.repository.OrderItemRepository;
import edu.mum.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired
    OrderItemRepository orderItemRepository;

    @Override
    public OrderItem saveOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    @Override
    public List<OrderItem> getOrderItems() {
        return (List<OrderItem>) orderItemRepository.findAll();
    }

    @Override
    public List<OrderItem> getOrderItemsWithNotNullReviews() {
        return orderItemRepository.getOrderItemWithNotNullReviews();
    }

    @Override
    public OrderItem getOrderItemById(Long id) {
        return orderItemRepository.findById(id).get();
    }

    @Override
    public List<String> getApprovedReviews(Long itemId) {
        return orderItemRepository.getApprovedReviews(itemId);
    }

    @Override
    public void deleteOrderItem(Long id) {
        orderItemRepository.delete(orderItemRepository.findById(id).get());
    }

}
