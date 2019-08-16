package edu.mum.controller;

import edu.mum.domain.Order;
import edu.mum.domain.OrderItem;
import edu.mum.domain.OrderItemStatus;
import edu.mum.domain.OrderStatus;
import edu.mum.domain.view.OrderInfo;
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
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;

    @GetMapping("/buyer/orders/{orderId}")
    public String getOrder(@PathVariable("orderId") Long orderId, Model model) {
        Order order = orderService.getOrderById(orderId);
        if (order.getStatus() == OrderStatus.PROCESSING) {
            for (OrderItem item : order.getOrderItems()) {
                if (item.getOrderStatus() == OrderItemStatus.ORDERED || item.getOrderStatus() == OrderItemStatus.SHIPPED) {
                    model.addAttribute("order", order);
                    return "/buyer/OrderDetail";
                }
            }
            order.setStatus(OrderStatus.COMPLETED);
            order.setEndDate(LocalDateTime.now());
            order.getBuyer().setPoints(order.getBuyer().getPoints() + order.getTotalAmount().divide(new BigDecimal(100)).intValue());
            orderService.updateOrder(order);
        }
        model.addAttribute("order", order);
        model.addAttribute("deliveredOrderItems", orderService.getDeliveredOrderItemsByOrder(orderId));
        return "/buyer/OrderDetail";
    }

    @PostMapping("/buyer/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId, Model model) {
        Order order = orderService.getOrderById(orderId);
        orderService.cancelOrder(order);
        return "redirect:/buyer/orders/" + orderId;
    }

    @PostMapping("/buyer/orders/{orderId}/download")
    public String downloadReceipt(@PathVariable("orderId") Long orderId, Model model, HttpServletResponse response) throws Exception {
        Order order = orderService.getOrderById(orderId);
        List<OrderItem> deliveredOrderItems = orderService.getDeliveredOrderItemsByOrder(orderId);
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId(order.getId());
        orderInfo.setBillingAddress(order.getBillingAddress());
        orderInfo.setBuyer(order.getBuyer());
        orderInfo.setPaymentInfo(order.getPaymentInfo());
        orderInfo.setPaymentMethod(order.getPaymentMethod());
        orderInfo.setTotalAmount(order.getTotalAmount());
        orderInfo.setOrderItems(deliveredOrderItems);
        File file = orderService.downloadReceipt(orderInfo);
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
        return "redirect:/buyer/orders/" + orderId;
    }

    @PostMapping("/buyer/orders/delete/{orderId}")
    public String deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        return "redirect:/buyer/orders";
    }

}
