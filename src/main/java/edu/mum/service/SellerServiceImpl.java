package edu.mum.service;

import edu.mum.domain.Buyer;
import edu.mum.domain.Seller;
import edu.mum.domain.User;
import edu.mum.repository.SellerRepository;
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
}
