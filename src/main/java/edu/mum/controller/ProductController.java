package edu.mum.controller;

import edu.mum.domain.CartItem;
import edu.mum.domain.OrderItem;
import edu.mum.domain.Product;
import edu.mum.domain.User;
import edu.mum.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ProductController {
    @Autowired
    ProductService productService;

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    CartItemService cartItemService;

    @Autowired
    UserService userService;

    @Autowired
    BuyerService buyerService;

    @GetMapping("/product/{productId}")
    public String loadProduct(@PathVariable("productId") Long id, Model model){
        Product product = productService.getProductById(id);

        model.addAttribute("product", product);
        List<OrderItem> orderItems = orderItemService.getOrderItems().stream().filter(x -> x.getProduct().equals(product)).collect(Collectors.toList());
        model.addAttribute("orderItems", orderItems);
        Double rating = 0.0;
        if(orderItems.size() != 0) {
            rating = orderItems.stream().mapToDouble(x -> x.getRating()).average().getAsDouble();
        }
        model.addAttribute("rating", rating);

        return "/buyer/product";
    }

    @GetMapping("/product/addToCart/{productId}")
    public String addToCart(@PathVariable("productId") Long productId){
        Product newProduct = productService.getProductById(productId);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = new User();
        if(!auth.getPrincipal().equals("anonymousUser")){
            String email = auth.getName();
            user = userService.findByEmail(email);

        } else {
            return "redirect:/account/login";
        }
        CartItem newCartItem = new CartItem();
        newCartItem.setProduct(newProduct);
        newCartItem.setBuyer(buyerService.getBuyerByUser(user));
        newCartItem.setQuantity(1);
        cartItemService.saveCartItem(newCartItem);
        return "redirect:/";
    }
}
