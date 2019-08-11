package edu.mum.service;

import edu.mum.domain.*;

import java.util.List;

public interface BuyerService {
    public Buyer saveBuyer(Buyer buyer);
    public Buyer getBuyerById(Long id);
    public void followSeller(Buyer buyer, Seller seller);
    public void unfollowSeller(Buyer buyer, Seller seller);
    public List<Orders> getOrdersByBuyerId(Long buyerId);
    public void addReview(OrderItem item, String review);
}
