package edu.mum.controller;

import edu.mum.domain.Product;
import edu.mum.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    ProductService productService;

    // get index page
    @GetMapping(value = {"/"})
    public String index(Model model) {
        //hardcoding product
        Product product1 = new Product();
        Product product2 = new Product();
        Product product3 = new Product();
        Product product4 = new Product();
        product1.setName("coffee");
        product2.setName("tv");
        product3.setName("bed");
        product4.setName("bedroom");
        product1.setDescription("dark coffee");
        product2.setDescription("led 42 inches");
        product3.setDescription("king bed");
        product4.setDescription("king bed");
        product1.setPrice(100.0);
        product2.setPrice(20.3);
        product3.setPrice(44.99);
        product4.setPrice(94.99);
        productService.saveProduct(product1);
        productService.saveProduct(product2);
        productService.saveProduct(product3);
        productService.saveProduct(product4);
        //--
        List<Product> products = productService.getProducts();
        model.addAttribute("products", products);
        return "index";
    }

    // 404 page
    @GetMapping("/404")
    public String get404() {
        return "404";
    }

    // 403 page
    @GetMapping("/403")
    public String get403(Principal user, Model model) {
        if (user != null) {
            model.addAttribute("msg", "Hi " + user.getName() + ", you do not have permission to access this page!");
        } else {
            model.addAttribute("msg", "You do not have permission to access this page!");
        }
        return "403";
    }

    // buyer homepage
    @GetMapping("/buyer")
    public String buyerHomepage() {
        return "/buyer/index";
    }

    // seller homepage
    @GetMapping("/seller")
    public String sellerHomepage() {
        return "/seller/index";
    }

    // admin homepage
    @GetMapping("/admin")
    public String adminHomepage() {
        return "/admin/index";
    }
}
