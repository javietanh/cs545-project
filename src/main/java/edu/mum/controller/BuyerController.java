package edu.mum.controller;

import edu.mum.domain.Buyer;
import edu.mum.domain.Seller;
import edu.mum.service.BuyerService;
import edu.mum.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class BuyerController {
    @Autowired
    private BuyerService buyerService;

    @Autowired
    private SellerService sellerService;

    @GetMapping("/register/buyer")
    public String inputBuyer(@ModelAttribute("buyer") Buyer buyer) {
        return "BuyerForm";
    }

    @PostMapping("/register/buyer")
    public String saveBuyer(@Valid Buyer buyer, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "BuyerForm";
        }
        buyerService.saveBuyer(buyer);
        Long buyerId = buyer.getId();
        return "redirect:/buyer/profile/{buyerId}";
    }

    @GetMapping("/buyer/profile/{buyerId}")
    public String getBuyerProfile(@PathVariable("buyerId") Long buyerId, Model model) {
        model.addAttribute("buyer", buyerService.getBuyerById(buyerId));
        return "BuyerPage";
    }

    @GetMapping("/buyer/profile/{buyerId}/update")
    public String updateBuyer(@PathVariable Long buyerId, Model model) {
        model.addAttribute("buyer", buyerService.getBuyerById(buyerId));
        return "BuyerForm";
    }

    @GetMapping("/buyer/home/{buyerId}")
    public String getBuyerHome(@PathVariable Long buyerId, Model model) {
        model.addAttribute("buyer", buyerService.getBuyerById(buyerId));
        return "index";
    }

    @PostMapping("/buyer/{buyerId}/seller/{sellerId}/follow")
    public String followSeller(@PathVariable("buyerId") Long buyerId, @PathVariable("sellerId") Long sellerId, Model model) {
        Buyer buyer = buyerService.getBuyerById(buyerId);
        Seller seller = sellerService.getSellerById(sellerId);
        buyerService.followSeller(buyer, seller);
        model.addAttribute("buyer", buyer);
        model.addAttribute("seller", seller);
        return "SellerPage";
    }

}
