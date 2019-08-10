package edu.mum.service;

import edu.mum.domain.Buyer;
import edu.mum.domain.Seller;

public interface BuyerService {
    public Buyer saveBuyer(Buyer buyer);
    public Buyer getBuyerById(Long id);
    public void followSeller(Buyer buyer, Seller seller);
    public void unfollowSeller(Buyer buyer, Seller seller);
}
