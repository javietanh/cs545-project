package edu.mum.controller;

import edu.mum.domain.Buyer;
import edu.mum.domain.CartItem;
import edu.mum.domain.User;
import edu.mum.service.BuyerService;
import edu.mum.service.CartService;
import edu.mum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private BuyerService buyerService;

    @Autowired
    private UserService userService;

    @GetMapping("/buyer/cart")
    @ResponseBody
    public List<CartItem> getCart(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        Buyer buyer = buyerService.getBuyerByUser(user);
        return cartService.getCartByBuyerId(buyer.getId());
    }

    @PostMapping("/buyer/cart/add")
    @ResponseBody
    public CartItem saveCartItem(@Valid CartItem item) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        Buyer buyer = buyerService.getBuyerByUser(user);
        return cartService.saveCartItem(buyer, item);
    }

    @DeleteMapping("/cart/remove")
    @ResponseBody
    public void removeCartItem(@RequestBody Long id) {
        cartService.removeCartItem(id);
    }


}
