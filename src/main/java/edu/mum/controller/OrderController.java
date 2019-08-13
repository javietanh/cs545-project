package edu.mum.controller;

import edu.mum.domain.Order;
import edu.mum.service.BuyerService;
import edu.mum.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;

    @GetMapping("/orders/{orderId}")
    public String getOrder(@PathVariable("orderId") Long orderId, Model model) {
        model.addAttribute("order", orderService.getOrderById(orderId));
        return "/buyer/OrderDetail";
    }

    @PostMapping("/orders/{orderId}/complete")
    public String completeOrder(@PathVariable("orderId") Long orderId, Model model) {
        Order order = orderService.getOrderById(orderId);
        orderService.completeOrder(order);
        return "redirect:/buyer/orders/";
    }

    @PostMapping("/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId, Model model) {
        Order order = orderService.getOrderById(orderId);
        orderService.cancelOrder(order);
        return "redirect:/buyer/orders";
    }

    @PostMapping("/orders/{orderId}/download")
    public String downloadReceipt(@PathVariable("orderId") Long orderId, Model model, HttpServletResponse response) throws Exception {
        Order order = orderService.getOrderById(orderId);
        File file = orderService.downloadReceipt(order);
        if (file.exists()) {
            response.setContentType("application/pdf");
            response.addHeader("Content-Disposition", "attachment; filename=order.pdf");
            ServletOutputStream stream = null;
            BufferedInputStream buf = null;
            try {
                stream = response.getOutputStream();
                FileInputStream os = new FileInputStream(file);
                int readBytes = 0;
                byte [] buffer = new byte [4096];
                while ((readBytes = os.read (buffer,0,4096)) != -1) {
                    stream.write (buffer,0,readBytes);
                }
            } catch (IOException ioe) {
                throw new ServletException(ioe.getMessage());
            } finally {
                if(stream != null)
                    stream.close();
                if(buf != null)
                    buf.close();
            }
        }
        return "redirect:/orders/" + orderId;
    }


}
