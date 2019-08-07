package edu.mum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // get index page
    @GetMapping(value = {"/"})
    public String index() {
        return "index";
    }

    // 404 page
    @GetMapping("/404")
    public String get404() {
        return "404";
    }

    // 403 page
    @GetMapping("/403")
    public String get403() {
        return "403";
    }

    // buyer homepage
    @GetMapping("/buyer")
    public String buyerHomepage(){
        return "/buyer/index";
    }

    // seller homepage
    @GetMapping("/seller")
    public String sellerHomepage(){
        return "/seller/index";
    }

    // admin homepage
    @GetMapping("/admin")
    public String adminHomepage(){
        return "/admin/index";
    }
}
