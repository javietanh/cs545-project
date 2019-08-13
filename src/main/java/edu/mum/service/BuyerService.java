package edu.mum.service;

import edu.mum.domain.*;

import java.util.List;

public interface BuyerService {
    Buyer saveBuyer(Buyer buyer);
    Buyer updateBuyer(Buyer buyer);
    Buyer getBuyerById(Long id);
    Buyer getBuyerByUser(User user);
    void followSeller(Buyer buyer, Seller seller);
    void unfollowSeller(Buyer buyer, Seller seller);
    List<Order> getOrdersByBuyerId(Long buyerId);
    void addReview(OrderItem item, String review);
    List<Seller> getFollowings(Long buyerId);
}
