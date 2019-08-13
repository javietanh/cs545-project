package edu.mum.controller;

import edu.mum.domain.Buyer;
import edu.mum.domain.CartItem;
import edu.mum.domain.User;
import edu.mum.domain.view.CartInfo;
import edu.mum.service.BuyerService;
import edu.mum.service.CartService;
import edu.mum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private BuyerService buyerService;

    @Autowired
    private UserService userService;

    @GetMapping(value = {"/buyer/cart"})
    public String getShoppingCartForm(){
        return "/buyer/ShoppingCart";
    }

    @GetMapping("/buyer/shoppingCart")
    @ResponseBody
    public List<CartInfo> getCart(Model model) {
        Authentication auth  = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        Buyer buyer = buyerService.getBuyerByUser(user);
        List<CartItem> cartItems = cartService.getCartByBuyerId(buyer.getId());
        List<CartInfo> cart = new ArrayList<CartInfo>();
        for (CartItem ci : cartItems){
            CartInfo c = new CartInfo();
            c.setId(ci.getId());
            c.setProductName(ci.getProduct().getName());
            c.setProductPrice(ci.getProduct().getPrice());
            c.setQuantity(ci.getQuantity());
            cart.add(c);
        }
        return cart;
    }

    @PostMapping("/buyer/cart/add")
    @ResponseBody
    public CartItem saveCartItem(@Valid CartItem item) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        Buyer buyer = buyerService.getBuyerByUser(user);
        return cartService.saveCartItem(buyer, item);
    }

    @DeleteMapping(value = "/cart/remove/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Boolean removeCartItem(@PathVariable Long id) {
        cartService.removeCartItem(id);
        return true;
    }


}
