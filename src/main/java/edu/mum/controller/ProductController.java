package edu.mum.controller;

import edu.mum.domain.OrderItem;
import edu.mum.domain.Product;
import edu.mum.service.OrderItemService;
import edu.mum.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ProductController {
    @Autowired
    ProductService productService;

    @Autowired
    OrderItemService orderItemService;

    @GetMapping("product/{productId}")
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
}
