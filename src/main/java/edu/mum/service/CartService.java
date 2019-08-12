package edu.mum.service;

import edu.mum.domain.Buyer;
import edu.mum.domain.CartItem;

import java.math.BigDecimal;
import java.util.List;

public interface CartService {
    public CartItem saveCartItem(Buyer buyer, CartItem item);
    public void removeCartItem(Long id);
    public List<CartItem> getCartByBuyerId(Long buyerId);
    public BigDecimal getTotalAmount(Long buyerId);
}
