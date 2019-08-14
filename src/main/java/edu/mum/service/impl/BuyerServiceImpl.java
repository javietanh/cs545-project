package edu.mum.service.impl;

import edu.mum.domain.*;
import edu.mum.repository.BuyerRepository;
import edu.mum.repository.OrderItemRepository;
import edu.mum.service.BuyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    public Buyer updateBuyer(Buyer buyer) {
        Buyer persistedBuyer = getBuyerById(buyer.getId());
        persistedBuyer.setUser(buyer.getUser());
        System.out.println(persistedBuyer);
        return buyerRepository.save(persistedBuyer);
    }

    @Override
    public Buyer getBuyerById(Long id) {
        return buyerRepository.findById(id).get();
    }

    @Override
    public Buyer getBuyerByUser(User user) {
        return buyerRepository.findBuyerByUser(user);
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
    public List<Order> getOrdersByBuyerId(Long buyerId) {
        return buyerRepository.findById(buyerId).get().getOrders();
    }

    @Override
    public void addReview(OrderItem item, String review) {
        item.setReview(review);
        item.setReviewDate(LocalDateTime.now());
        orderItemRepository.save(item);
    }

    @Override
    public List<Seller> getFollowings(Long buyerId) {
        return buyerRepository.findById(buyerId).get().getSellers();
    }

}
