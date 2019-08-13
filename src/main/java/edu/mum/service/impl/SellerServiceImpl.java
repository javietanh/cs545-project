package edu.mum.service.impl;

import edu.mum.domain.Seller;
import edu.mum.domain.User;
import edu.mum.repository.SellerRepository;
import edu.mum.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerServiceImpl implements SellerService {
    @Autowired
    SellerRepository sellerRepository;

    @Override
    public Seller getSellerById(Long id) {
        return sellerRepository.findById(id).get();
    }

    @Override
    public Seller save(Seller seller) {
        return sellerRepository.save(seller);
    }

    @Override
    public Seller updateSeller(Seller seller) {
        Seller selectSeller = sellerRepository.findById(seller.getId()).get();
        selectSeller.setName(seller.getName());
        selectSeller.setDescription(seller.getDescription());
        selectSeller.setPicture(seller.getPicture());
        return sellerRepository.save(selectSeller);
    }
}
