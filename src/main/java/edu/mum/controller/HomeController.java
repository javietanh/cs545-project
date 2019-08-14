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
import org.springframework.web.bind.annotation.RequestMapping;

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
    private UserService userService;

    @Autowired
    private BuyerService buyerService;

    @Autowired
    private CartService cartService;

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

    @RequestMapping(value = {"/product/{id}/cart"})
    public String addProductToCart(@PathVariable(value = "id") Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated()){
            return "redirect:/account/login";
        }

        User user = userService.findByEmail(authentication.getName());
        if(user == null){
            return "redirect:/account/login";
        }

        // query product to add to cart
        Product product = productService.findById(id);
        Buyer buyer = buyerService.getBuyerByUser(user);

        // create new cart item and add to shopping cart.
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setBuyer(buyer);
        cartItem.setQuantity(1);

        cartService.addCartItem(cartItem);

        return "redirect:/buyer/cart";
    }

}
