package edu.mum.service;

import edu.mum.domain.Buyer;
import edu.mum.domain.CartItem;

import java.math.BigDecimal;
import java.util.List;

public interface CartService {
    void addCartItem(CartItem item);
    CartItem saveCartItem(Buyer buyer, CartItem item);
    void removeCartItem(Long id);
    List<CartItem> getCartByBuyerId(Long buyerId);
    BigDecimal getTotalAmount(Long buyerId);
}
