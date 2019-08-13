package edu.mum.controller;

import edu.mum.ShoppingApplication;
import edu.mum.domain.Advert;
import edu.mum.service.AdvertService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

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

        MultipartFile uploadAdvert = advert.getImageUpload();
        String homeUrl = new ApplicationHome(ShoppingApplication.class).getDir() + "\\static\\img\\adverts";
        Path rootLocation = Paths.get(homeUrl);

        if (!Files.exists(rootLocation)) {
            try {
                Files.createDirectory(rootLocation);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//
//        String avatarName = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(uploadAvatar.getOriginalFilename());

        String advertName = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(uploadAdvert.getOriginalFilename());

        if (uploadAdvert != null && !uploadAdvert.isEmpty()) {
            try {
                Files.copy(uploadAdvert.getInputStream(), rootLocation.resolve(advertName));
                advert.setImage("/img/adverts/" + advertName);
            } catch (Exception ex) {
                bindingResult.rejectValue("uploadAdvert", "", "Problem on saving advert picture.");
            }
        }
        advertService.saveAdvert(advert);

        return "redirect:/admin/ads";
    }
}
