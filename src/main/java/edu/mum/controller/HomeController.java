package edu.mum.controller;

import edu.mum.domain.*;
import edu.mum.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    @Autowired
    ProductService productService;

    @Autowired
    AdvertService advertService;

    @Autowired
    UserService userService;

    @Autowired
    BuyerService buyerService;

    @Autowired
    CartItemService cartItemService;

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    CategoryService categoryService;

    // get index page
    @GetMapping(value = {"/"})
    public String index(Model model) {
        //brings products
        List<Product> products = productService.getAll();
        model.addAttribute("products", products);
        //brings the ads
        List<Advert> adverts = advertService.getAdverts();
        model.addAttribute("adverts", adverts);
        //brings categories
        List<Category> categories = categoryService.getCategories();
        model.addAttribute("categories", categories);


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
    @GetMapping(value = {"/buyer", "/buyer/dashboard"})
    public String buyerHomepage() {
        return "/buyer/dashboard";
    }

    // admin homepage
    @GetMapping(value = {"/admin/dashboard", "/admin"})
    public String adminHomepage() {
        return "/admin/dashboard.html";
    }

    @GetMapping("/product/{productId}")
    public String loadProduct(@PathVariable("productId") Long id, Model model){
        Product product = productService.findById(id);

        model.addAttribute("product", product);
        List<OrderItem> orderItems = orderItemService.getOrderItems().stream().filter(x -> x.getProduct().equals(product)).collect(Collectors.toList());
        model.addAttribute("orderItems", orderItems);
        Double rating = 0.0;
        if(orderItems.size() != 0) {
            rating = orderItems.stream().mapToDouble(x -> x.getRating()).average().getAsDouble();
        }
        model.addAttribute("rating", rating);

        return "product";
    }

    @GetMapping("/product/addToCart/{productId}")
    public String addToCart(@PathVariable("productId") Long productId){
        Product newProduct = productService.findById(productId);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user;
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
