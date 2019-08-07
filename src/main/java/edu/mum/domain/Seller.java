package edu.mum.domain;

import lombok.Data;

import javax.persistence.*;
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
    private List<Product> products;
    private Boolean status;
    @ManyToMany(mappedBy = "sellers")
    private List<Buyer> buyers;
}
