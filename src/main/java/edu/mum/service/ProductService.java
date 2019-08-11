package edu.mum.service;

import edu.mum.domain.Product;

import java.util.List;

public interface ProductService {
    public Product saveProduct(Product product);

    public List<Product> getProducts();

    public Product getProductById(Long id);
}
