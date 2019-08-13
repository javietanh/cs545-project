package edu.mum.controller;

import edu.mum.domain.Advert;
import edu.mum.domain.Product;
import edu.mum.service.AdvertService;
import edu.mum.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    ProductService productService;

    @Autowired
    AdvertService advertService;

    // get index page
    @GetMapping(value = {"/"})
    public String index(Model model) {
        //brings products
        List<Product> products = productService.getAll();
        model.addAttribute("products", products);
        //brings the ads
        List<Advert> adverts = advertService.getAdverts();
        model.addAttribute("adverts", adverts);


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
}
