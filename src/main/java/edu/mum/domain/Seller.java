package edu.mum.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    private String picture;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @Valid
    private User user;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToMany(mappedBy = "sellers")
    private List<Buyer> buyers = new ArrayList<>();

    public void addBuyer(Buyer buyer) {
        buyers.add(buyer);
    }

    public void removeBuyer(Buyer buyer) {
        buyers.remove(buyer);
    }
}
