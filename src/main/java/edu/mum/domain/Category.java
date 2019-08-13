package edu.mum.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Category {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    List<Product> products = new ArrayList<Product>();

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }
}
