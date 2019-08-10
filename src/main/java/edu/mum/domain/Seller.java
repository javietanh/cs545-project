package edu.mum.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Seller {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<Product>();
    private Boolean status;
    @ManyToMany(mappedBy = "sellers")
    private List<Buyer> buyers = new ArrayList<Buyer>();

    public void addBuyer(Buyer buyer) {
        buyers.add(buyer);
    }

    public void removeBuyer(Buyer buyer) {
        buyers.remove(buyer);
    }
}
