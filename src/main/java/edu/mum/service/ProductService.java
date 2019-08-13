package edu.mum.service;

import edu.mum.domain.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAll();

    Product findById(Long id);

    Product save(Product product);

    void delete(Product product);
}
