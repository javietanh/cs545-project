package edu.mum.controller;

import edu.mum.ShoppingApplication;
import edu.mum.domain.*;
import edu.mum.domain.view.ChangePasswordDto;
import edu.mum.domain.view.UpdateProfileDto;
import edu.mum.service.BuyerService;
import edu.mum.service.MessageService;
import edu.mum.service.SellerService;
import edu.mum.service.UserService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

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

    @GetMapping(value = {"/message"})
    public String getMessageListForm(Model model){
        // get the current user message.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null){
            User user = userService.findByEmail(authentication.getName());
            if(user != null){
                List<Message> messages = user.getMessages();
                model.addAttribute("messages", messages);
            }
        }

        return "/account/messages";
    }

    @GetMapping(value = {"/messages"},
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public @ResponseBody
    List<Message> getUserMessages() {
        // get current user principal
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            List<Message> messages = userService.getLast5UnreadNotifyMessageByUserEmail(auth.getName());
            return messages;
        }

        return null;
    }

    @GetMapping(value = {"/profile"})
    public String getProfileForm(Model model) {

        if (!model.containsAttribute("profile")) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                User user = userService.findByEmail(authentication.getName());
                UpdateProfileDto profileDto = new UpdateProfileDto();
                profileDto.setFirstName(user.getFirstName());
                profileDto.setLastName(user.getLastName());
                profileDto.setEmail(user.getEmail());
                profileDto.setPhone(user.getPhone());
                profileDto.setAddress(user.getAddress());
                model.addAttribute("profile", profileDto);

                // get the buyer points
                Buyer buyer= buyerService.getBuyerByUser(user);
                if(buyer != null) {
                    model.addAttribute("points", buyer.getPoints());
                }
            }
        }

        if (!model.containsAttribute("changePasswordDto")) {
            model.addAttribute("changePasswordDto", new ChangePasswordDto());
        }

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
    @PostMapping(value = {"/profile/info"})
    public String updateProfile(@Valid @ModelAttribute("profile") UpdateProfileDto profile,
                                BindingResult result,
                                RedirectAttributes rd,
                                HttpServletRequest request) {

        // validate user email unique if user change the email.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = null;

        if (authentication != null) {
            currentUser = userService.findByEmail(authentication.getName());
            if (currentUser != null && !currentUser.getEmail().equals(profile.getEmail())) {
                User existsUser = userService.findByEmail(profile.getEmail());
                if (existsUser != null) {
                    // reject with error when email is not unique
                    result.rejectValue("email", "email.exists", "There is already a user registered with the email provided.");
                }
            }
        }

        // update user data.
        currentUser.setFirstName(profile.getFirstName());
        currentUser.setLastName(profile.getLastName());
        currentUser.setEmail(profile.getEmail());
        currentUser.setPhone(profile.getPhone());
        currentUser.setAddress(profile.getAddress());

        // process update user avatar.
        MultipartFile uploadAvatar = profile.getUploadAvatar();
        String homeUrl = new ApplicationHome(ShoppingApplication.class).getDir() + "\\static\\img\\avatar";
        Path rootLocation = Paths.get(homeUrl);

        if (!Files.exists(rootLocation)) {
            try {
                Files.createDirectory(rootLocation);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String avatarName = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(uploadAvatar.getOriginalFilename());

        if (uploadAvatar != null && !uploadAvatar.isEmpty()) {
            try {
                Files.copy(uploadAvatar.getInputStream(), rootLocation.resolve(avatarName));
                currentUser.setAvatar("/img/avatar/" + avatarName);
            } catch (Exception ex) {
                result.rejectValue("uploadAvatar", "", "Problem on saving user picture.");
            }
        }

        // errors, show the errors
        if (result.hasErrors()) {
            rd.addFlashAttribute("org.springframework.validation.BindingResult.profile", result);
            rd.addFlashAttribute("profile", profile);
            return "redirect:/account/profile";
        }

        // update user info.
        userService.updateUser(currentUser);

        // add success message
        rd.addFlashAttribute("updateProfileSuccess", "Profile Updated.");

        return "redirect:/account/profile";
    }


    @PostMapping(value = {"/profile/security"})
    public String changePassword(@Valid @ModelAttribute("changePasswordDto") ChangePasswordDto changePasswordDto, BindingResult result, RedirectAttributes rd) {

        // get current user.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {

            User user = userService.findByEmail(authentication.getName());

            if (user != null) {

                if (changePasswordDto.getNewPassword() != "" || changePasswordDto.getCurrentPassword() != "" || changePasswordDto.getConfirmNewPassword() != "") {
                    if (changePasswordDto.getNewPassword() != "") {
                        if (!changePasswordDto.getNewPassword().equals(changePasswordDto.getConfirmNewPassword())) {
                            result.rejectValue("confirmNewPassword", "", "New Password and Confirm New Password fields miss matched.");
                        }

                        if (changePasswordDto.getCurrentPassword() == "") {
                            result.rejectValue("currentPassword", "", "Please provide your current password.");
                        }
                    }

                    if (changePasswordDto.getCurrentPassword() != "") {
                        // check the current password is correct.
                        boolean valid = userService.validatePassword(changePasswordDto.getCurrentPassword(), user);

                        if (!valid) {
                            result.rejectValue("currentPassword", "", "Current Password is incorrect.");
                        }

                        // errors, show the errors
                        if (result.hasErrors()) {
                            rd.addFlashAttribute("org.springframework.validation.BindingResult.changePasswordDto", result);
                            rd.addFlashAttribute("changePasswordDto", changePasswordDto);
                            return "redirect:/account/profile";
                        }

                        // everything good, change user password.
                        userService.changePassword(changePasswordDto.getNewPassword(), user);

                        // add success message
                        rd.addFlashAttribute("changePasswordSuccess", "Password Changed.");
                    }
                }
            }
        }

        return "redirect:/account/profile";
    }

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
        User saveUser = userService.addUser(user);

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
        User saveUser = userService.addUser(newUser);

        // set seller association to the new user.
        seller.setUser(saveUser);

        // default set seller status to unapproved.
        seller.setStatus(Status.PENDING);

        // create new seller
        sellerService.save(seller);

        // moving to success page.
        return "redirect:/account/register-success";
    }

    /*
        DELETE
     */
    @DeleteMapping(value = {"/messages/read/{id}"},
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    boolean setMessageRead(@PathVariable(value = "id") Long id) {
        messageService.setMessageRead(id);
        return true;
    }

    @GetMapping(value = {"/message/read/{id}"})
    public String readMessage(@PathVariable(value = "id") Long id){
        Message message = messageService.getMessageById(id);
        message.setRead(true);
        messageService.saveMessage(message);
        return "redirect:/account/message";
    }

    @GetMapping(value = {"/message/delete/{id}"})
    public String deleteMessage(@PathVariable(value = "id") Long id){
        Message message = messageService.getMessageById(id);
        messageService.delete(message);
        return "redirect:/account/message";
    }

}
