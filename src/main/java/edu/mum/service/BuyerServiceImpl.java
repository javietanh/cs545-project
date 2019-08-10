package edu.mum.service;

import edu.mum.domain.Buyer;
import edu.mum.domain.Role;
import edu.mum.domain.Seller;
import edu.mum.repository.BuyerRepository;
import edu.mum.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuyerServiceImpl implements BuyerService {
    @Autowired
    private BuyerRepository buyerRepository;

    @Autowired
    private SellerRepository sellerRepository;

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
}
