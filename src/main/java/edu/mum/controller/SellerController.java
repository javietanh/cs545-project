package edu.mum.controller;

import edu.mum.domain.*;
import edu.mum.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import edu.mum.ShoppingApplication;
import edu.mum.domain.view.SellerDto;
import org.apache.commons.io.FilenameUtils;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
@RequestMapping(value = {"/seller"})
public class SellerController {
    @Autowired
    private SellerService sellerService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private OrderItemService orderItemService;

    @ModelAttribute("categories")
    public List<Category> getCategories(){
        return categoryService.getCategories();
    }
  
    @Autowired
    private MessageService messageService;

    @GetMapping("/orders")
    public String getOrdersBySeller(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        Seller seller = sellerService.getSellerByUser(user);
        List<OrderItem> orderItems = orderService.getOrderItemsBySeller(seller.getId());
        model.addAttribute("orderItems", orderItems);
        return "/seller/Orders";
    }

    @GetMapping("/orders/{itemId}")
    public String getOrderItem(@PathVariable("itemId") Long itemId, Model model) {
        OrderItem item = orderService.getOrderItemById(itemId);
        BigDecimal totalPrice = item.getProduct().getPrice().multiply(new BigDecimal(item.getQuantity()));
        model.addAttribute("item", item);
        model.addAttribute("totalPrice", totalPrice);
        return "/seller/OrderItemDetail";
    }

    @PostMapping("/orders/delete/{itemId}")
    public String deleteOrderItem(@PathVariable("itemId") Long itemId, Model model) {
        orderItemService.deleteOrderItem(itemId);
        return "redirect:/seller/orders";
    }

    @PostMapping("/orders/{itemId}/status")
    public String updateOrderStatus(@PathVariable("itemId") Long itemId, @Valid OrderItem item, Model model) {
        OrderItem orderItem = orderService.getOrderItemById(itemId);
        BigDecimal totalPrice = orderItem.getProduct().getPrice().multiply(new BigDecimal(orderItem.getQuantity()));
        if(orderItem != null){
            orderItem.setOrderStatus(item.getOrderStatus());
            if (orderItem.getOrderStatus() == OrderItemStatus.CANCELED) {
                String message = "The order for " + orderItem.getProduct().getName() + " is canceled";
                messageService.sendMessageToUser(orderItem.getOrder().getBuyer().getUser(), message);
            }
            if (orderItem.getOrder().getStatus() == OrderStatus.NEW) {
                if (orderItem.getOrderStatus() != OrderItemStatus.ORDERED && orderItem.getOrderStatus() != OrderItemStatus.CANCELED) {
                    orderItem.getOrder().setStatus(OrderStatus.PROCESSING);
                }
            }
            if (orderItem.getOrderStatus() == OrderItemStatus.CANCELED || orderItem.getOrderStatus() == OrderItemStatus.RETURNED) {
                orderItem.getOrder().setTotalAmount(orderItem.getOrder().getTotalAmount().subtract(totalPrice));
            }
            orderService.saveOrderItem(orderItem);
        }
        model.addAttribute("item", orderItem);
        model.addAttribute("totalPrice", totalPrice);
        return "redirect:/seller/orders/";
    }

    @GetMapping("/followers")
    public String getFollowers(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        Seller seller = sellerService.getSellerByUser(user);
        List<Buyer> buyers = sellerService.getFollowers(seller.getId());
        model.addAttribute("buyers", buyers);
        return "/seller/Followers";
    }

    @GetMapping(value = {"", "/", "/dashboard"})
    public String getSellerIndex() {
        return "seller/dashboard";
    }

    @GetMapping(value = {"/shop"})
    public String getShopForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            User user = userService.findByEmail(authentication.getName());
            if (user != null) {
                SellerDto dto = new SellerDto();
                dto.setId(user.getSeller().getId());
                dto.setName(user.getSeller().getName());
                dto.setDescription(user.getSeller().getDescription());
                dto.setPicture(user.getSeller().getPicture() != null ? user.getSeller().getPicture() : "data:image/svg+xml;charset=UTF-8,%3Csvg%20width%3D%22893%22%20height%3D%22180%22%20xmlns%3D%22http%3A%2F%2Fwww.w3.org%2F2000%2Fsvg%22%20viewBox%3D%220%200%20893%20180%22%20preserveAspectRatio%3D%22none%22%3E%3Cdefs%3E%3Cstyle%20type%3D%22text%2Fcss%22%3E%23holder_16c89944157%20text%20%7B%20fill%3Argba(255%2C255%2C255%2C.75)%3Bfont-weight%3Anormal%3Bfont-family%3AHelvetica%2C%20monospace%3Bfont-size%3A45pt%20%7D%20%3C%2Fstyle%3E%3C%2Fdefs%3E%3Cg%20id%3D%22holder_16c89944157%22%3E%3Crect%20width%3D%22893%22%20height%3D%22180%22%20fill%3D%22%23777%22%3E%3C%2Frect%3E%3Cg%3E%3Ctext%20x%3D%22331.4000015258789%22%20y%3D%22110.15999908447266%22%3E893x180%3C%2Ftext%3E%3C%2Fg%3E%3C%2Fg%3E%3C%2Fsvg%3E");
                dto.setCreated(user.getRegisterDate());
                dto.setStatus(user.getSeller().getStatus().toString());
                model.addAttribute("seller", dto);
            }
        }
        return "/seller/shopInfo";
    }

    @PostMapping(value = {"/shop"})
    public String updateShopInfo(@Valid SellerDto dto, BindingResult result, RedirectAttributes rd) {
        // process upload shop picture.
        MultipartFile uploadPicture = dto.getUpload();
        String homeUrl = new ApplicationHome(ShoppingApplication.class).getDir() + "\\static\\img\\shop";
        Path rootLocation = Paths.get(homeUrl);

        if (!Files.exists(rootLocation)) {
            try {
                Files.createDirectory(rootLocation);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String pictureName = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(uploadPicture.getOriginalFilename());

        if (uploadPicture != null && !uploadPicture.isEmpty()) {
            try {
                Files.copy(uploadPicture.getInputStream(), rootLocation.resolve(pictureName));
                dto.setPicture("/img/shop/" + pictureName);
            } catch (Exception ex) {
                result.rejectValue("uploadPicture", "", "Problem on saving shop picture.");
            }
        }

        if (result.hasErrors()) {
            return "/seller/shopInfo";
        }

        // save the shop info
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            User userUpdate = userService.findByEmail(authentication.getName());
            if (userUpdate != null) {
                Seller seller = userUpdate.getSeller();
                seller.setName(dto.getName());
                seller.setDescription(dto.getDescription());
                seller.setPicture(dto.getPicture());
                sellerService.updateSeller(seller);
            }
        }

        // redirect with message.
        rd.addFlashAttribute("success", "Shop Information Updated.");

        return "redirect:/seller/shop";
    }
}
