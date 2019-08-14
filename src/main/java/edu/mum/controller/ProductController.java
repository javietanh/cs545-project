package edu.mum.controller;

import edu.mum.ShoppingApplication;
import edu.mum.domain.CartItem;
import edu.mum.domain.OrderItem;
import edu.mum.domain.Product;
import edu.mum.domain.User;
import edu.mum.service.*;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = {"/seller/product"})
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private UserService userService;

    @Autowired
    private BuyerService buyerService;
    @Autowired
    private CartItemService cartItemService;

    //hey mr David


    @GetMapping(value = {"", "/", "/list"})
    public String getProductList(Model model) {
        model.addAttribute("products", productService.getAll());
        return "/seller/productList";
    }

    @GetMapping(value = {"/delete/{id}"})
    public String deleteProduct(@PathVariable(value = "id") Long id){
        Product product = productService.findById(id);
        if(product != null)
            productService.delete(product);
        return "redirect:/";
    }

    @GetMapping(value = {"/add"})
    public String addProductForm(Model model){
        model.addAttribute("product", new Product());
        return "/seller/productEdit";
    }

    @GetMapping(value = {"/{id}"})
    public String editProduct(@PathVariable(value = "id", required = false) Long id, Model model){
        if(id != null){
            model.addAttribute("product", productService.findById(id));
        }
        return "/seller/productEdit";
    }

    @PostMapping(value = {"/{id}"})
    public String saveProduct(@Valid Product product, BindingResult result, @PathVariable(value = "id", required = false) Long id){

        // upload file.
        MultipartFile upload = product.getUpload();
        String homeUrl = new ApplicationHome(ShoppingApplication.class).getDir() + "\\static\\img\\products";
        Path rootLocation = Paths.get(homeUrl);

        if (!Files.exists(rootLocation)) {
            try {
                Files.createDirectory(rootLocation);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (upload != null && !upload.isEmpty()) {
            try {
                String imageName = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(upload.getOriginalFilename());
                Files.copy(upload.getInputStream(), rootLocation.resolve(imageName));
                product.setImage("/img/products/" + imageName);
            } catch (Exception ex) {
                result.rejectValue("upload", "", "Problem on saving product picture.");
            }
        }

        if(result.hasErrors()) {
            return "/seller/productEdit";
        }

        // get the product.
        if(id != null){
            Product updateProduct = productService.findById(id);
            updateProduct.setName(product.getName());
            updateProduct.setDescription(product.getDescription());
            updateProduct.setPrice(product.getPrice());
            updateProduct.setImage(product.getImage());
            updateProduct.setAvailable(product.getAvailable());
            productService.save(updateProduct);
        }else{
            productService.save(product);
        }

        return "redirect:/seller/product";
    }



}
