package edu.mum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdvertismentController {
    @GetMapping("/admin/ads")
    String showAdvertismentPanel(Model model){
        return "/admin/advertisment";
    }
}
