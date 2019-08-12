package edu.mum.controller;

import edu.mum.domain.*;
import edu.mum.service.BuyerService;
import edu.mum.service.MessageService;
import edu.mum.service.SellerService;
import edu.mum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/account")
public class AccountController {

    private UserService userService;
    private SellerService sellerService;
    private BuyerService buyerService;

    @Autowired
    private MessageService messageService;

    public AccountController(UserService userService, SellerService sellerService, BuyerService buyerService) {
        this.userService = userService;
        this.sellerService = sellerService;
        this.buyerService = buyerService;
    }

    /*
        Get Request
     */

    @GetMapping(value = {"/messages"},
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public @ResponseBody
    List<Message> getUserMessages() {
        // get current user principal
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null && auth.isAuthenticated()){
            List<Message> messages = userService.getLast5UnreadNotifyMessageByUserEmail(auth.getName());
            return messages;
        }

        return null;
    }

    @GetMapping(value = {"/profile"})
    public String getProfileForm() {
        return "/account/profile";
    }


    @GetMapping(value = {"/login"})
    public String getLoginForm() {
        return "/account/login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        // You can redirect wherever you want, but generally it's a good practice to
        return "redirect:/";
    }

    @GetMapping(value = {"/register"})
    public String getRegisterPage() {
        return "/account/register";
    }

    @GetMapping(value = {"/seller-register"})
    public String getSellerRegisterForm(@ModelAttribute("seller") Seller seller) {
        return "/account/sellerRegister";
    }

    @GetMapping(value = {"/buyer-register"})
    public String getBuyerRegisterForm(@ModelAttribute("user") User user) {
        return "/account/buyerRegister";
    }

    @GetMapping(value = {"/register-success"})
    public String getSuccessPage() {
        return "/account/register-success";
    }

    /*
        Post Request
     */
    @PostMapping(value = {"/buyer-register"})
    public String postBuyerRegister(@Valid User user, BindingResult result) {

        // check if the email is unique
        User existsUser = userService.findByEmail(user.getEmail());
        if (existsUser != null) {
            // reject with error when email is not unique
            result.rejectValue("email", "email.exists", "There is already a user registered with the email provided.");
        }

        // validation data.
        if (result.hasErrors()) {
            return "/account/buyerRegister";
        }

        // call service save new user as a buyer
        // set user role as BUYER
        user.setRole(Role.BUYER);

        // create new user.
        User saveUser = userService.save(user);

        // create new buyer
        Buyer buyer = new Buyer();

        // set buyer association with new user.
        buyer.setUser(saveUser);

        // save new buyer into database
        buyerService.saveBuyer(buyer);

        // moving to success page.
        return "redirect:/account/register-success";
    }

    @PostMapping(value = {"/seller-register"})
    public String postSellerRegister(@Valid Seller seller, BindingResult result) {

        // check if the email is unique
        User existsUser = userService.findByEmail(seller.getUser().getEmail());
        if (existsUser != null) {
            // reject with error when email is not unique
            result.rejectValue("user.email", "email.exists", "There is already a user registered with the email provided.");
        }

        // validate input data.
        if (result.hasErrors()) {
            return "/account/sellerRegister";
        }

        // call service save new user as a buyer
        User newUser = seller.getUser();

        // set user role as SELLER
        newUser.setRole(Role.SELLER);

        // persisted user to database.
        User saveUser = userService.save(newUser);

        // set seller association to the new user.
        seller.setUser(saveUser);

        // default set seller status to unapproved.
        seller.setStatus(SellerStatus.PENDING);

        // create new seller
        sellerService.save(seller);

        // moving to success page.
        return "redirect:/account/register-success";
    }

    /*
        DELETE
     */
    @DeleteMapping(value = {"/messages"}, produces = MediaType.APPLICATION_JSON_VALUE,
                                          consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody void setMessageRead(@RequestBody Long id){
        messageService.setMessageRead(id);
    }

}
