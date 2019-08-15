package edu.mum.controller;

import edu.mum.domain.*;
import edu.mum.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    @Autowired
    private ProductService productService;

    @Autowired
    private AdvertService advertService;

    @Autowired
    private UserService userService;

    @Autowired
    private BuyerService buyerService;

    @Autowired
    private CartService cartService;

    @Autowired
    private CategoryService categoryService;



    // get index page
    @GetMapping(value = {"/"})
    public String index(Model model) {
        //brings products
        List<Product> products = productService.getAll();

        Collections.shuffle(products, new Random());

        model.addAttribute("products", products);
        //brings the ads
        List<Advert> adverts = advertService.getAdverts();
        model.addAttribute("adverts", adverts);
        //brings categories
        List<Category> categories = categoryService.getCategories();
        model.addAttribute("categories", categories);



        return "index";
    }


    @GetMapping("/search")
    public String indexSearch(@RequestParam("searchWord") String searchWord ,Model model) {
        //brings products
        List<Product> products = productService.getAll().stream()
                .filter(x -> x.getName().toLowerCase().contains(searchWord.toLowerCase())).collect(Collectors.toList());

        Collections.shuffle(products, new Random());

        model.addAttribute("products", products);
        //brings the ads
        List<Advert> adverts = advertService.getAdverts();
        model.addAttribute("adverts", adverts);
        //brings categories
        List<Category> categories = categoryService.getCategories();
        model.addAttribute("categories", categories);



        return "index";
    }


    // add product to shopping cart.
    @PostMapping(value = {"/product/addToCart"},
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public boolean addProductToCart(@RequestBody String id) {
        // get current user.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null){
            User user = userService.findByEmail(authentication.getName());
            if(user == null)
                return false;

            // get product.
            Product product = productService.findById(Long.parseLong(id));
            Buyer buyer = buyerService.getBuyerByUser(user);
            List<CartItem> cartItems = buyer.getCartItems();
            CartItem cartItem = new CartItem();

            for(CartItem item : cartItems){
                if(item.getProduct().getId().equals(product.getId())){
                    cartItem = item;
                    cartItem.setQuantity(cartItem.getQuantity() + 1);
                    break;
                }
            }

            // make new cart item with the product id.
            if(cartItem.getId() == null){
                cartItem.setProduct(product);
                cartItem.setBuyer(buyer);
                cartItem.setQuantity(1);
            }

            cartService.saveCartItem(buyer, cartItem);

        }else{
            return false;
        }
        return true;
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

    // common error page
    @GetMapping("/error")
    public String getError() {
        return "error";
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
        List<CartItem> cartItems = buyer.getCartItems();
        CartItem cartItem = new CartItem();

        for(CartItem item : cartItems){
            if(item.getProduct().getId().equals(product.getId())){
                cartItem = item;
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                break;
            }
        }

        // make new cart item with the product id.
        if(cartItem.getId() == null){
            cartItem.setProduct(product);
            cartItem.setBuyer(buyer);
            cartItem.setQuantity(1);
        }


        cartService.addCartItem(cartItem);

        return "redirect:/buyer/cart";
    }

    @GetMapping("/category")
    public String getProductsByCategoryPage(@RequestParam("id") Long categoryId, Model model) {
        Category category = categoryService.getCategoryById(categoryId);
        List<Product> products = productService.getProductsByCategory(category);

        Collections.shuffle(products, new Random());

        model.addAttribute("products", products);
        //brings the ads
        List<Advert> adverts = advertService.getAdverts();
        model.addAttribute("adverts", adverts);
        //brings categories
        List<Category> categories = categoryService.getCategories();
        model.addAttribute("categories", categories);

        model.addAttribute("currentCategoryId", category.getId());

        return "index";
    }

}
