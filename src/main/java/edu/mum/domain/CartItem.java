package edu.mum.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JoinColumn(name = "product_id")
    private Product product;
    private int quantity;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "buyer_id")
    @JsonIgnore
    private Buyer buyer;
}
