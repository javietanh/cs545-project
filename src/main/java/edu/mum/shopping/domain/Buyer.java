package edu.mum.shopping.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Buyer {
    @Id
    @GeneratedValue
    private Long id;
    private Integer points;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL)
    private List<Orders> orders;
    @OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL)
    private List<CartItem> cartItems;
    @ManyToMany
    @JoinTable(name = "following", joinColumns = {@JoinColumn(name = "buyer_id")}, inverseJoinColumns = {@JoinColumn(name = "seller_id")})
    private List<Seller> sellers;

}
