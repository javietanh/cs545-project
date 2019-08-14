package edu.mum.controller;

import com.mashape.unirest.http.exceptions.UnirestException;
import edu.mum.domain.*;
import edu.mum.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private AdvertService advertService;

    // admin homepage
    @GetMapping(value = {"/dashboard", "/", ""})
    public String adminHomepage(Model model) {

        // get total reviews.
        Long totoReviews = orderItemService
                .getOrderItems()
                .stream()
                .filter(x-> x.getReview() != null)
                .count();
        model.addAttribute("totalReviews", totoReviews);

        // get total orders
        List<Order> orderList = orderService.getAll();

        Long totalOrders = orderList
                .stream()
                .count();

        Long totalCompletedOrders = orderList
                .stream()
                .filter(x->x.getStatus() == OrderStatus.COMPLETED)
                .count();

        Long totalCancelOrders = orderList
                .stream()
                .filter(x->x.getStatus() == OrderStatus.CANCELED)
                .count();

        Long totalProcessingOrders = orderList
                .stream()
                .filter(x->x.getStatus() == OrderStatus.PROCESSING)
                .count();

        model.addAttribute("totalOrders", totalOrders);
        model.addAttribute("totalCompletedOrders", totalCompletedOrders);
        model.addAttribute("totalCancelOrders", totalCancelOrders);
        model.addAttribute("totalProcessingOrders", totalProcessingOrders);

        // get total products.
        int totalProducts = productService.getAll().size();
        model.addAttribute("totalProducts", totalProducts);

        // get total sellers
        Long totalSellers = sellerService.getAllSellers()
                .stream()
                .filter(x->x.getStatus() == Status.APPROVED)
                .count();

        model.addAttribute("totalActiveSellers", totalSellers);

        // get total ads
        int totalAds = advertService.getAll().size();
        model.addAttribute("totalAds", totalAds);


        return "/admin/dashboard";

    }

    @GetMapping("/sellers")
    public String getSellers(Model model) {
        model.addAttribute("sellers", sellerService.getAllSellers());
        return "/admin/Seller";
    }

    @PostMapping("/sellers/{sellerId}/approve")
    public String approveSeller(@PathVariable("sellerId") Long sellerId, Model model) {
        Seller s = sellerService.getSellerById(sellerId);
        if (s != null) {
            s.setStatus(Status.APPROVED);
            sellerService.save(s);
        }
        model.addAttribute("seller", s);
        return "redirect:/admin/sellers";
    }

    @PostMapping("/sellers/{sellerId}/reject")
    public String rejectSeller(@PathVariable("sellerId") Long sellerId, Model model) {
        Seller s = sellerService.getSellerById(sellerId);
        if (s != null) {
            s.setStatus(Status.REJECTED);
            sellerService.save(s);
        }
        model.addAttribute("seller", s);
        return "redirect:/admin/sellers";
    }

    @GetMapping("/reviews")
    public String getReviews(Model model) {
        model.addAttribute("orderItems", orderItemService.getOrderItemsWithNotNullReviews());
        return "/admin/Reviews";
    }

    @PostMapping("/reviews/{itemId}/approve")
    public String approveReview(@PathVariable("itemId") Long itemId, Model model) throws UnirestException {
        OrderItem orderItem = orderItemService.getOrderItemById(itemId);
        if (orderItem != null) {
            orderItem.setReviewStatus(Status.APPROVED);
            orderItemService.saveOrderItem(orderItem);
            String subject = "JPA Market: Approve Review";
            String content = "Your review for the product " + orderItem.getProduct().getName() + " has been approved.";
            messageService.sendEmail("ja.vietanh@gmail.com", orderItem.getOrder().getBuyer().getUser().getEmail(), subject, content);
        }
        model.addAttribute("item", orderItem);
        return "redirect:/admin/reviews";
    }

    @PostMapping("/reviews/{itemId}/reject")
    public String rejectReview(@PathVariable("itemId") Long itemId, Model model) throws UnirestException {
        OrderItem orderItem = orderItemService.getOrderItemById(itemId);
        if (orderItem != null) {
            orderItem.setReviewStatus(Status.REJECTED);
            orderItemService.saveOrderItem(orderItem);
            String subject = "JPA Market: Reject Review";
            String content = "Your review for the product " + orderItem.getProduct().getName() + " has been rejected.";
            messageService.sendEmail("noreply@shopping.com", orderItem.getOrder().getBuyer().getUser().getEmail(), subject, content);
        }
        model.addAttribute("item", orderItem);
        return "redirect:/admin/reviews";
    }


}
