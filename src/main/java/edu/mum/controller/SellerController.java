package edu.mum.controller;

import edu.mum.domain.Buyer;
import edu.mum.domain.OrderItem;
import edu.mum.domain.Seller;
import edu.mum.domain.User;
import edu.mum.service.OrderService;
import edu.mum.service.SellerService;
import edu.mum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@Controller
public class SellerController {
    @Autowired
    private SellerService sellerService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @GetMapping("/seller/orders")
    public String getOrdersBySeller(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        Seller seller = sellerService.getSellerByUser(user);
        List<OrderItem> orderItems = orderService.getOrderItemsBySeller(seller.getId());
        model.addAttribute("orderItems", orderItems);
        return "/seller/Orders";
    }

    @GetMapping("/seller/orders/{itemId}")
    public String getOrderItem(@PathVariable("itemId") Long itemId, Model model) {
        OrderItem item = orderService.getOrderItemById(itemId);
        BigDecimal totalPrice = item.getProduct().getPrice().multiply(new BigDecimal(item.getQuantity()));
        model.addAttribute("item", item);
        model.addAttribute("totalPrice", totalPrice);
        return "/seller/OrderItemDetail";
    }

    @PostMapping("/seller/orders/{itemId}/status")
    public String updateOrderStatus(@PathVariable("itemId") Long itemId, @Valid OrderItem item, Model model) {
        OrderItem orderItem = orderService.getOrderItemById(itemId);
        if(orderItem != null){
            orderItem.setOrderStatus(item.getOrderStatus());
            orderService.saveOrderItem(orderItem);
        }
        BigDecimal totalPrice = orderItem.getProduct().getPrice().multiply(new BigDecimal(orderItem.getQuantity()));
        model.addAttribute("item", orderItem);
        model.addAttribute("totalPrice", totalPrice);
        return "redirect:/seller/orders/" + itemId;
    }

    @GetMapping("/seller/followers")
    public String getFollowers(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        Seller seller = sellerService.getSellerByUser(user);
        List<Buyer> buyers = sellerService.getFollowers(seller.getId());
        model.addAttribute("buyers", buyers);
        return "/seller/Followers";
    }
}
