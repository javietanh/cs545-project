package edu.mum.service.impl;

import edu.mum.domain.*;
import edu.mum.repository.BuyerRepository;
import edu.mum.repository.OrderItemRepository;
import edu.mum.service.BuyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuyerServiceImpl implements BuyerService {
    @Autowired
    private BuyerRepository buyerRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public Buyer saveBuyer(Buyer buyer) {
        buyer.getUser().setRole(Role.BUYER);
        return buyerRepository.save(buyer);
    }

    @Override
    public Buyer getBuyerById(Long id) {
        return buyerRepository.findById(id).get();
    }

    @Override
    public void followSeller(Buyer buyer, Seller seller) {
        buyer.followSeller(seller);
        seller.addBuyer(buyer);
        buyerRepository.save(buyer);
    }

    @Override
    public void unfollowSeller(Buyer buyer, Seller seller) {
        buyer.unfollowSeller(seller);
        seller.removeBuyer(buyer);
        buyerRepository.save(buyer);
    }

    @Override
    public List<Orders> getOrdersByBuyerId(Long buyerId) {
        return buyerRepository.findById(buyerId).get().getOrders();
    }

    @Override
    public void addReview(OrderItem item, String review) {
        item.setReview(review);
        orderItemRepository.save(item);
    }
}