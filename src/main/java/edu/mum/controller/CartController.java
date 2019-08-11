package edu.mum.controller;

import edu.mum.domain.Buyer;
import edu.mum.domain.CartItem;
import edu.mum.service.BuyerService;
import edu.mum.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private BuyerService buyerService;

    @GetMapping("/buyer/{buyerId}/cart")
    public String getCart(@PathVariable("buyerId") Long buyerId, Model model) {
        model.addAttribute("cart", cartService.getCartByBuyerId(buyerId));
        return "/buyer/ShoppingCart";
    }

    @PostMapping("/buyer/{buyerId}/cart/add")
    public CartItem saveCartItem(@PathVariable("buyerId") Long buyerId, @Valid CartItem item) {
        Buyer buyer = buyerService.getBuyerById(buyerId);
        return cartService.saveCartItem(buyer, item);
    }

    @PostMapping("/buyer/{buyerId}/cart/remove/{id}")
    public void removeCartItem(@PathVariable("buyerId") Long buyerId, @PathVariable("id") Long itemId) {
        Buyer buyer = buyerService.getBuyerById(buyerId);
        cartService.removeCartItem(buyer, itemId);
    }


}
