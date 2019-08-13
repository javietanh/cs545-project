package edu.mum.service.impl;

import edu.mum.domain.*;
import edu.mum.domain.view.OrderItemInfo;
import edu.mum.repository.CartRepository;
import edu.mum.repository.OrderItemRepository;
import edu.mum.repository.OrderRepository;
import edu.mum.service.OrderService;
import edu.mum.util.PdfGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private PdfGenerator pdfGenerator;

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).get();
    }

    @Override
    public Order saveOrder(Buyer buyer, Order order) {
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
        if (order.getUsingPoints() == true) {
            totalAmount = totalAmount.subtract(new BigDecimal(buyer.getPoints()));
            if (totalAmount.compareTo(BigDecimal.ZERO) > 0) {
                buyer.setPoints(0);
            } else {
                buyer.setPoints(totalAmount.abs().intValue());
            }
        }
        order.setTotalAmount(totalAmount);
        order.setBuyer(buyer);
        order.setOrderedDate(LocalDateTime.now());
        buyer.addOrder(order);
        return orderRepository.save(order);
    }

    @Override
    public OrderItem saveOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    @Override
    public OrderItem updateOrderItem(OrderItem orderItem) {
        OrderItem persistedOrderItem = getOrderItemById(orderItem.getId());
        persistedOrderItem.setProduct(orderItem.getProduct());
        persistedOrderItem.setQuantity(orderItem.getQuantity());
        persistedOrderItem.setOrder(orderItem.getOrder());
        persistedOrderItem.setReview(orderItem.getReview());
        persistedOrderItem.setReviewStatus(orderItem.getReviewStatus());
        persistedOrderItem.setRating(orderItem.getRating());
        persistedOrderItem.setOrderStatus(orderItem.getOrderStatus());
        return orderItemRepository.save(persistedOrderItem);
    }

    @Override
    public void completeOrder(Orders order) {
        order.setStatus(OrderStatus.COMPLETED);
        Integer points = order.getTotalAmount().divide(new BigDecimal(100)).intValue();
        order.getBuyer().setPoints(order.getBuyer().getPoints() + points);
        orderRepository.save(order);
    }

    @Override
    public void cancelOrder(Order order) {
        order.setStatus(OrderStatus.CANCELED);
        orderRepository.save(order);
    }

    @Override
    public File downloadReceipt(Order order) throws Exception {
        Map<String, Order> data = new HashMap<String, Order>();
        data.put("order", order);
        return pdfGenerator.createPdf("buyer/PDF", data);

    }

    @Override
    public OrderItem getOrderItemById(Long itemId) {
        return orderItemRepository.findById(itemId).get();
    }

    @Override
    public List<OrderItem> getOrderItemsBySeller(Long sellerId) {
        return orderItemRepository.getOrderItemsBySeller(sellerId);
    }



}
