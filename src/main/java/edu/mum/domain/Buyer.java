package edu.mum.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Buyer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer points = 0;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<Order>();
    @OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL)
    private List<CartItem> cartItems = new ArrayList<CartItem>();
    @ManyToMany
    @JoinTable(name = "following", joinColumns = {@JoinColumn(name = "buyer_id")}, inverseJoinColumns = {@JoinColumn(name = "seller_id")})
    private List<Seller> sellers = new ArrayList<Seller>();

    public void followSeller(Seller seller) {
        sellers.add(seller);
    }

    public void unfollowSeller(Seller seller) {
        sellers.remove(seller);
    }

    public void addCartItem(CartItem item) {
        cartItems.add(item);
    }

    public void removeCartItem(CartItem item) {
        cartItems.remove(item);
    }

    public void addOrder(Order order) {
        orders.add(order);
    }
}
