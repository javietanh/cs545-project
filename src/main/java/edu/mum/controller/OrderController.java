package edu.mum.controller;

import edu.mum.domain.Buyer;
import edu.mum.domain.Order;
import edu.mum.domain.Order;
import edu.mum.service.BuyerService;
import edu.mum.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;

    @GetMapping("/orders/{orderId}")
    public String getOrder(@PathVariable("orderId") Long orderId, Model model) {
        model.addAttribute("order", orderService.getOrderByOrderId(orderId));
        return "/buyer/OrderDetail";
    }

    @PostMapping("/orders/{orderId}/complete")
    public String completeOrder(@PathVariable("orderId") Long orderId, Model model) {
        Order order = orderService.getOrderByOrderId(orderId);
        orderService.completeOrder(order);
        return "redirect:/buyer/orders/";
    }

    @PostMapping("/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId, Model model) {
        Order order = orderService.getOrderByOrderId(orderId);
        orderService.cancelOrder(order);
        return "redirect:/buyer/orders";
    }
}
