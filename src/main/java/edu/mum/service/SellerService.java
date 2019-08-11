package edu.mum.service;

import edu.mum.domain.Seller;

public interface SellerService {
    Seller getSellerById(Long id);
    Seller save(Seller seller);
}
