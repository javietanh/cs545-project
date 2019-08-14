package edu.mum.controller;

import edu.mum.domain.OrderItem;
import edu.mum.domain.Seller;
import edu.mum.domain.Status;
import edu.mum.service.OrderItemService;
import edu.mum.service.SellerService;
import edu.mum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private OrderItemService orderItemService;

    @GetMapping("/sellers")
    public String getSellers(Model model) {
        model.addAttribute("sellers", sellerService.getAllSellers());
        return "/admin/Seller";
    }

    @PostMapping("/sellers/{sellerId}/approve")
    public String approveSeller(@PathVariable("sellerId") Long sellerId, Model model) {
        Seller s = sellerService.getSellerById(sellerId);
        if(s != null){
            s.setStatus(Status.APPROVED);
            sellerService.save(s);
        }
        model.addAttribute("seller", s);
        return "redirect:/admin/sellers";
    }

    @PostMapping("/sellers/{sellerId}/reject")
    public String rejectSeller(@PathVariable("sellerId") Long sellerId, Model model) {
        Seller s = sellerService.getSellerById(sellerId);
        if(s != null){
            s.setStatus(Status.REJECTED);
            sellerService.save(s);
        }
        model.addAttribute("seller", s);
        return "redirect:/admin/sellers";
    }

    @GetMapping("/reviews")
    public String getReviews(Model model) {
        model.addAttribute("orderItems", orderItemService.getOrderItems());
        return "/admin/Reviews";
    }

    @PostMapping("/reviews/{itemId}/approve")
    public String approveReview(@PathVariable("itemId") Long itemId, Model model) {
        OrderItem orderItem = orderItemService.getOrderItemById(itemId);
        if(orderItem != null){
            orderItem.setReviewStatus(Status.APPROVED);
            orderItemService.saveOrderItem(orderItem);
        }
        model.addAttribute("item", orderItem);
        return "redirect:/admin/reviews";
    }

    @PostMapping("/reviews/{itemId}/reject")
    public String rejectReview(@PathVariable("itemId") Long itemId, Model model) {
        OrderItem orderItem = orderItemService.getOrderItemById(itemId);
        if(orderItem != null){
            orderItem.setReviewStatus(Status.REJECTED);
            orderItemService.saveOrderItem(orderItem);
        }
        model.addAttribute("item", orderItem);
        return "redirect:/admin/reviews";
    }


}
