package edu.mum.controller;

import edu.mum.domain.Buyer;
import edu.mum.domain.CartItem;
import edu.mum.domain.Product;
import edu.mum.domain.User;
import edu.mum.domain.view.CartInfo;
import edu.mum.service.BuyerService;
import edu.mum.service.CartItemService;
import edu.mum.service.CartService;
import edu.mum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
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

    @Autowired
    private CartItemService cartItemService;

    @GetMapping(value = {"/buyer/cart"})
    public String getShoppingCartForm() {
        return "/buyer/ShoppingCart";
    }

    @GetMapping("/buyer/shoppingCart")
    @ResponseBody
    public List<CartInfo> getCart() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            User user = userService.findByEmail(auth.getName());
            if (user != null) {
                Buyer buyer = buyerService.getBuyerByUser(user);
                if (buyer != null) {
                    List<CartItem> cartItems = cartService.getCartByBuyerId(buyer.getId());
                    List<CartInfo> items = new ArrayList<>();
                    for (CartItem ci : cartItems) {
                        Product product = ci.getProduct();
                        items.add(new CartInfo(ci.getId(),
                                product.getName(),
                                product.getPrice(),
                                product.getImage(),
                                ci.getQuantity()
                        ));
                    }

                    return items;
                }
            }
        }

        return null;
    }

    @PostMapping("/buyer/cart/add")
    @ResponseBody
    public CartItem saveCartItem(@Valid CartItem item) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        Buyer buyer = buyerService.getBuyerByUser(user);
        return cartService.saveCartItem(buyer, item);
    }

    @PutMapping(value = "/buyer/cart/{id}/increaseQuantity", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public CartItem increaseQuantity(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        Buyer buyer = buyerService.getBuyerByUser(user);
        CartItem i = cartItemService.getCartItemById(id);
        i.setQuantity(i.getQuantity() + 1);
        return cartService.saveCartItem(buyer, i);
    }

    @PutMapping(value = "/buyer/cart/{id}/decreaseQuantity", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public CartItem decreaseQuantity(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        Buyer buyer = buyerService.getBuyerByUser(user);
        CartItem i = cartItemService.getCartItemById(id);
        if (i.getQuantity() > 1) {
            i.setQuantity(i.getQuantity() - 1);
        }
        return cartService.saveCartItem(buyer, i);
    }

    @DeleteMapping(value = "/buyer/cart/remove/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Boolean removeCartItem(@PathVariable Long id) {
        cartService.removeCartItem(id);
        return true;
    }


}
