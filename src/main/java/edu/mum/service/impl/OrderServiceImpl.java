package edu.mum.service.impl;

import edu.mum.domain.*;
import edu.mum.repository.CartRepository;
import edu.mum.repository.OrderRepository;
import edu.mum.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Override
    public Orders getOrderByOrderId(Long id) {
        return orderRepository.findById(id).get();
    }

    @Override
    public Orders saveOrder(Buyer buyer, Orders order) {
        List<CartItem> cartItems = (List) cartRepository.getCartItemByBuyerId(buyer.getId());
        BigDecimal totalAmount = new BigDecimal(0.00);
        for (CartItem ci : cartItems) {
            OrderItem oi = new OrderItem();
            oi.setProduct(ci.getProduct());
            oi.setQuantity(ci.getQuantity());
            order.addOrderItem(oi);
            oi.setOrder(order);
            totalAmount = totalAmount.add(ci.getProduct().getPrice().multiply(new BigDecimal(ci.getQuantity())));
            cartRepository.delete(ci);
        }
        order.setTotalAmount(totalAmount);
        order.setBuyer(buyer);
        order.setOrderedDate(LocalDateTime.now());
        buyer.addOrder(order);
        return orderRepository.save(order);
    }

    @Override
    public void completeOrder(Orders order) {
        order.setStatus(OrderStatus.COMPLETED);
        Integer points = order.getTotalAmount().divide(new BigDecimal(100)).intValue();
        order.getBuyer().setPoints(order.getBuyer().getPoints() + points);
        orderRepository.save(order);
    }

    @Override
    public void cancelOrder(Orders order) {
        order.setStatus(OrderStatus.CANCELED);
        orderRepository.save(order);
    }


}
