package edu.mum.controller;

import edu.mum.domain.*;
import edu.mum.domain.view.SellerInfo;
import edu.mum.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BuyerController {
    @Autowired
    private BuyerService buyerService;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    // buyer homepage
    @GetMapping(value = {"/buyer", "/buyer/dashboard"})
    public String buyerHomepage() {
        return "/buyer/dashboard";
    }

    @GetMapping("/register/buyer")
    public String inputBuyer(@ModelAttribute("buyer") Buyer buyer) {
        return "/buyer/BuyerForm";
    }

    @PostMapping("/register/buyer")
    public String saveNewBuyer(@Valid Buyer buyer, BindingResult result) {
        if (result.hasErrors()) {
            return "/buyer/BuyerForm";
        }
        buyerService.saveBuyer(buyer);
        Long buyerId = buyer.getId();
        return "redirect:/buyer/" + buyerId + "/profile";
    }

    @GetMapping("/buyer/profile")
    public String getBuyerProfile(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        model.addAttribute("buyer", buyerService.getBuyerByUser(user));
        return "/buyer/BuyerProfile";
    }

    @GetMapping("/buyer/profile/update")
    public String updateBuyer(@PathVariable Long buyerId, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        Buyer buyer = buyerService.getBuyerByUser(user);
        buyer.getUser().setConfirmPassword(buyer.getUser().getPassword());
        model.addAttribute("buyer", buyer);
        return "/buyer/UpdateBuyer";
    }

    @PostMapping("/buyer/profile/update")
    public String saveBuyer(@Valid Buyer buyer, BindingResult result) {
        if (result.hasErrors()) {
            return "/buyer/UpdateBuyer";
        }
        buyerService.updateBuyer(buyer);
        return "redirect:/buyer/profile";
    }

    @GetMapping("/buyer/following")
    public String getFollowing(){
        return "/buyer/Following";
    }

    @GetMapping("/buyer/followings")
    @ResponseBody
    public List<SellerInfo> getFollowings(Model model) {
        Authentication auth  = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        Buyer buyer = buyerService.getBuyerByUser(user);
        List<Seller> sellers = buyerService.getFollowings(buyer.getId());
        List<SellerInfo> followings = new ArrayList<SellerInfo>();
        for (Seller s : sellers){
            SellerInfo f = new SellerInfo();
            f.setId(s.getId());
            f.setName(s.getName());
            f.setDescription(s.getDescription());
            f.setPhone(s.getUser().getPhone());
            f.setEmail(s.getUser().getEmail());
            f.setAddress(s.getUser().getAddress());
            followings.add(f);
        }
        return followings;
    }

    @DeleteMapping("/buyer/following/unfollow/{sellerId}")
    @ResponseBody
    public Boolean removeCartItem(@PathVariable Long sellerId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        Buyer buyer = buyerService.getBuyerByUser(user);
        Seller seller = sellerService.getSellerById(sellerId);
        buyerService.unfollowSeller(buyer, seller);
        return true;
    }

    @PostMapping("/buyer/follow/{action}/{sellerId}")
    @ResponseBody
    public Boolean followSeller(@PathVariable("sellerId") Long sellerId, @PathVariable("action") String action){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        Buyer buyer = buyerService.getBuyerByUser(user);
        Seller seller = sellerService.getSellerById(sellerId);
        if(action.equals("Follow")){

        buyerService.followSeller(buyer, seller);
        return true;
        } else {
        buyerService.unfollowSeller(buyer, seller);
        return false;

        }
    }

    @GetMapping("/buyer/orders")
    public String getOrderHistory(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        Buyer buyer = buyerService.getBuyerByUser(user);
        model.addAttribute("orders", buyerService.getOrdersByBuyerId(buyer.getId()));
        return "/buyer/OrderHistory";
    }

    @GetMapping("/buyer/checkout")
    public String getCheckout(@ModelAttribute("order") Order order, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        Buyer buyer = buyerService.getBuyerByUser(user);
        model.addAttribute("cart", cartService.getCartByBuyerId(buyer.getId()));
        model.addAttribute("totalAmount", cartService.getTotalAmount(buyer.getId()));
        return "/buyer/Checkout";
    }

    @PostMapping("/buyer/order")
    public String placeOrder(@Valid Order order, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        Buyer buyer = buyerService.getBuyerByUser(user);
        orderService.saveOrder(buyer, order);
        Long orderId = order.getId();
        return "redirect:/buyer/orders/" + orderId;
    }

    @GetMapping("/buyer/item/{itemId}/review")
    public String getReview(@PathVariable("itemId") Long itemId, @ModelAttribute("review") String review, Model model) {
        model.addAttribute("item", orderService.getOrderItemById(itemId));
        return "/buyer/ReviewProduct";
    }

    @PostMapping("/buyer/item/{itemId}/review")
    public String saveReview(@PathVariable("itemId") Long itemId, @Valid String review) {
        OrderItem item = orderService.getOrderItemById(itemId);
        buyerService.addReview(item, review);
        Long orderId = item.getOrder().getId();
        return "redirect:/buyer/orders/" + orderId;
    }

    @PostMapping("/buyer/item/{itemId}/cancel")
    public String cancelOrderItem(@PathVariable("itemId") Long itemId, Model model) {
        OrderItem orderItem = orderService.getOrderItemById(itemId);
        BigDecimal totalPrice = orderItem.getProduct().getPrice().multiply(new BigDecimal(orderItem.getQuantity()));
        if(orderItem != null){
            orderItem.setOrderStatus(OrderItemStatus.CANCELED);
            orderItem.getOrder().setTotalAmount(orderItem.getOrder().getTotalAmount().subtract(totalPrice));
            orderService.saveOrderItem(orderItem);
        }
        model.addAttribute("item", orderItem);
        Long orderId = orderItem.getOrder().getId();
        return "redirect:/buyer/orders/" + orderId;
    }

}
