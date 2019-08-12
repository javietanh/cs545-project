package edu.mum.controller;

import edu.mum.domain.Buyer;
import edu.mum.domain.Orders;
import edu.mum.domain.Seller;
import edu.mum.service.BuyerService;
import edu.mum.service.CartService;
import edu.mum.service.OrderService;
import edu.mum.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @GetMapping("/buyer/{buyerId}/profile")
    public String getBuyerProfile(@PathVariable("buyerId") Long buyerId, Model model) {
        model.addAttribute("buyer", buyerService.getBuyerById(buyerId));
        return "/buyer/BuyerProfile";
    }

    @GetMapping("/buyer/{buyerId}/profile/update")
    public String updateBuyer(@PathVariable Long buyerId, Model model) {
        Buyer buyer = buyerService.getBuyerById(buyerId);
        buyer.getUser().setConfirmPassword(buyer.getUser().getPassword());
        model.addAttribute("buyer", buyer);
        return "/buyer/UpdateBuyer";
    }

    @PostMapping("/buyer/profile/update")
    public String saveBuyer(@Valid Buyer buyer, BindingResult result) {
        if (result.hasErrors()) {
            return "/buyer/UpdateBuyer";
        }
        Buyer updatedBuyer = buyerService.updateBuyer(buyer);
        Long buyerId = updatedBuyer.getId();
        return "redirect:/buyer/" + buyerId + "/profile";
    }

    @GetMapping("/buyer/home/{buyerId}")
    public String getBuyerHome(@PathVariable Long buyerId, Model model) {
        model.addAttribute("buyer", buyerService.getBuyerById(buyerId));
        return "index";
    }

    @PostMapping("/buyer/{buyerId}/seller/{sellerId}/follow")
    public String followSeller(@PathVariable("buyerId") Long buyerId, @PathVariable("sellerId") Long sellerId, Model model) {
        Buyer buyer = buyerService.getBuyerById(buyerId);
        Seller seller = sellerService.getSellerById(sellerId);
        buyerService.followSeller(buyer, seller);
        model.addAttribute("buyer", buyer);
        model.addAttribute("seller", seller);
        return "SellerPage";
    }

    @PostMapping("/buyer/{buyerId}/seller/{sellerId}/unfollow")
    public String unfollowSeller(@PathVariable("buyerId") Long buyerId, @PathVariable("sellerId") Long sellerId, Model model) {
        Buyer buyer = buyerService.getBuyerById(buyerId);
        Seller seller = sellerService.getSellerById(sellerId);
        buyerService.unfollowSeller(buyer, seller);
        model.addAttribute("buyer", buyer);
        model.addAttribute("seller", seller);
        return "SellerPage";
    }

    @GetMapping("/buyer/{buyerId}/orders")
    public String getOrderHistory(@PathVariable("buyerId") Long buyerId, Model model) {
        model.addAttribute("orders", buyerService.getOrdersByBuyerId(buyerId));
        return "/buyer/OrderHistory";
    }

    @GetMapping("/buyer/{buyerId}/checkout")
    public String getCheckout(@PathVariable("buyerId") Long buyerId, @ModelAttribute("order") Orders order, Model model) {
        model.addAttribute("cart", cartService.getCartByBuyerId(buyerId));
        model.addAttribute("totalAmount", cartService.getTotalAmount(buyerId));
        model.addAttribute("buyer", buyerService.getBuyerById(buyerId));
        return "/buyer/Checkout";
    }

    @PostMapping("/buyer/{buyerId}/order")
    public String placeOrder(@PathVariable("buyerId") Long buyerId, @Valid Orders order, Model model) {
        Buyer buyer = buyerService.getBuyerById(buyerId);
        orderService.saveOrder(buyer, order);
        Long orderId = order.getId();
        return "redirect:/orders/" + orderId;
    }

}
