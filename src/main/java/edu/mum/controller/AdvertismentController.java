package edu.mum.controller;

import edu.mum.domain.Advert;
import edu.mum.service.AdvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.util.List;

@Controller
public class AdvertismentController {
    @Autowired
    AdvertService advertService;

    @Autowired
    ResourceLoader resourceLoader;

    @GetMapping("/admin/ads")
    String showAdvertismentPanel(@ModelAttribute("advert") Advert advert, Model model) {
        List<Advert> adverts = advertService.getAdverts();
        model.addAttribute("adverts", adverts);

        return "/admin/advertisment";
    }

    @PostMapping("/admin/addAdvert")
    String addAdvert(@Valid Advert advert, BindingResult bindingResult, RedirectAttributes ra, 
                     HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "/admin/advertisment";
        }
        advertService.saveAdvert(advert);

        return "redirect:/admin/ads";
    }
}
