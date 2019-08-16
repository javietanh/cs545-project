package edu.mum.controller;

import edu.mum.ShoppingApplication;
import edu.mum.domain.*;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
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

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private SellerService sellerService;

    // home page
    @GetMapping("/product/{productId}")
    public String loadProduct(@PathVariable("productId") Long id, Model model) {
        Product product = productService.findById(id);

        model.addAttribute("product", product);
        List<OrderItem> orderItems = orderItemService.getOrderItems().stream().filter(x -> x.getProduct().equals(product) && x.getReviewStatus().equals(Status.APPROVED)).collect(Collectors.toList());
        model.addAttribute("orderItems", orderItems);

        Seller seller = product.getSeller();
        model.addAttribute("seller", seller);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        Buyer buyer = buyerService.getBuyerByUser(user);
        if(buyer != null){
            if(buyerService.getFollowings(buyer.getId()).contains(seller)){
                model.addAttribute("follow",1);
            } else {
                model.addAttribute("follow", 2);
            }
        } else {
            model.addAttribute("follow", 0);
        }

        return "product";
    }

    @GetMapping("/product/addToCart/{productId}")
    public String addToCart(@PathVariable("productId") Long productId) {
        Product newProduct = productService.findById(productId);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = new User();
        if (!auth.getPrincipal().equals("anonymousUser")) {
            String email = auth.getName();
            user = userService.findByEmail(email);

        } else {
            return "redirect:/account/login";
        }
        CartItem newCartItem = new CartItem();
        newCartItem.setProduct(newProduct);
        newCartItem.setBuyer(buyerService.getBuyerByUser(user));
        newCartItem.setQuantity(1);
        cartItemService.saveCartItem(newCartItem);
        return "redirect:/";
    }

    // seller
    @GetMapping(value = {"/seller/product"})
    public String getProductList(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        Seller seller = sellerService.getSellerByUser(user);
        model.addAttribute("products", productService.getProductsBySeller(seller));
        return "/seller/productList";
    }

    @GetMapping(value = {"/seller/product/delete/{id}"})
    public String deleteProduct(@PathVariable(value = "id") Long id) {
        Product product = productService.findById(id);
        if (product != null)
            productService.delete(product);
        return "redirect:/seller/product";
    }

    @GetMapping(value = {"/seller/product/add"})
    public String addProductForm(Model model, RedirectAttributes rd) {
        // find the seller of this product.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Seller seller = null;
        if(authentication != null){
            User user = userService.findByEmail(authentication.getName());
            if(user != null){
                seller = user.getSeller();

                // if the seller is not approved yet, just return error and not allow for update.
                if(seller.getStatus() != Status.APPROVED){
                    rd.addFlashAttribute("error", "Unapproved Seller can not change the product.");
                    return "redirect:/error";
                }
            }
        }
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getCategories());
        return "/seller/productEdit";
    }

    @GetMapping(value = {"/seller/product/{id}"})
    public String editProduct(@PathVariable(value = "id", required = false) Long id, Model model, RedirectAttributes rd) {

        // find the seller of this product.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Seller seller = null;
        if(authentication != null){
            User user = userService.findByEmail(authentication.getName());
            if(user != null){
                seller = user.getSeller();

                // if the seller is not approved yet, just return error and not allow for update.
                if(seller.getStatus() != Status.APPROVED){
                    rd.addFlashAttribute("error", "Unapproved Seller can not change the product.");
                    return "redirect:/error";
                }
            }
        }

        if (id != null) {
            model.addAttribute("product", productService.findById(id));
            model.addAttribute("categories", categoryService.getCategories());
        }
        return "/seller/productEdit";
    }



    @PostMapping(value = {"/seller/product/{id}"})
    public String saveProduct(@Valid Product product, BindingResult result, @PathVariable(value = "id", required = false) Long id, RedirectAttributes rd) {

        // find the seller of this product.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Seller seller = null;
        if(authentication != null){
            User user = userService.findByEmail(authentication.getName());
            if(user != null){
                seller = user.getSeller();

                // if the seller is not approved yet, just return error and not allow for update.
                if(seller.getStatus() != Status.APPROVED){
                    rd.addFlashAttribute("error", "Unapproved Seller can not change the product.");
                    return "redirect:/error";
                }
            }
        }

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

        if (result.hasErrors()) {
            return "/seller/productEdit";
        }

        // load category of product.
        Category category = categoryService.getCategoryById(product.getCategory().getId());

        // get the product.
        if (id != null) {
            Product updateProduct = productService.findById(id);
            updateProduct.setName(product.getName());
            updateProduct.setDescription(product.getDescription());
            updateProduct.setPrice(product.getPrice());
            if(product.getImage() != null){
                updateProduct.setImage(product.getImage());
            }
            updateProduct.setAvailable(product.getAvailable());
            updateProduct.setCategory(category);
            productService.save(updateProduct);
        } else {
            product.setCategory(category);
            product.setSeller(seller);
            productService.save(product);

            // send notify message to followers
            List<Buyer> followers = seller.getBuyers();
            for(Buyer follower : followers){
                String message = "From " + seller.getName() +" shop: New product added.";
                messageService.sendMessageToUser(follower.getUser(), message);
            }
        }

        return "redirect:/seller/product";
    }

    @PostMapping(value = {"/seller/product"})
    public String saveProduct(@Valid Product product, BindingResult result, RedirectAttributes rd) {

        // find the seller of this product.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Seller seller = null;
        if(authentication != null){
            User user = userService.findByEmail(authentication.getName());
            if(user != null){
                seller = user.getSeller();

                // if the seller is not approved yet, just return error and not allow for update.
                if(seller.getStatus() != Status.APPROVED){
                    rd.addFlashAttribute("error", "Unapproved Seller can not change the product.");
                    return "redirect:/error";
                }
            }
        }

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

        if (result.hasErrors()) {
            return "/seller/productEdit";
        }

        // load category of product.
        Category category = categoryService.getCategoryById(product.getCategory().getId());

        // get the product.
        product.setCategory(category);
        product.setSeller(seller);
        productService.save(product);

        // send notify message to followers
        List<Buyer> followers = seller.getBuyers();
        for(Buyer follower : followers){
            String message = "From " + seller.getName() +" shop: New product added.";
            messageService.sendMessageToUser(follower.getUser(), message);
        }

        return "redirect:/seller/product";
    }
}
