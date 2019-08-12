package edu.mum.controller;

import edu.mum.domain.Product;
import edu.mum.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping("product/{productId}")
    public String loadProduct(@PathVariable("productId") Long id, Model model){
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "/buyer/product";
    }
}
