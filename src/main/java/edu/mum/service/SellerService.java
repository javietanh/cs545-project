package edu.mum.service;

import edu.mum.domain.Seller;
import edu.mum.domain.User;

public interface SellerService {
    Seller getSellerById(Long id);
    Seller save(Seller seller);
    Seller updateSeller(Seller seller);
}
