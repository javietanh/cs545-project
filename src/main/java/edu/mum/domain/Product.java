package edu.mum.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
public class Product {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String name;
    private String description;
    @Column(nullable = false)
    private Double price;
    private String image;
    private Boolean available;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "seller_id")
    private Seller seller;
}
