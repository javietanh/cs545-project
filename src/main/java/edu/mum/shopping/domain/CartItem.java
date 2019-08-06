package edu.mum.shopping.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
public class CartItem {
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    @JoinColumn(name="product_id")
    private Product product;
    private BigDecimal price;
    private int quantity;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "buyer_id")
    private Buyer buyer;
}
