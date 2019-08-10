package edu.mum.controller;

import edu.mum.domain.Seller;
import edu.mum.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/account")
public class AccountController {

    @GetMapping(value = {"/login"})
    public String getLoginForm() {
        return "/account/login";
    }

    @GetMapping(value = {"/register"})
    public String getRegisterPage(){
        return "/account/register";
    }

}
