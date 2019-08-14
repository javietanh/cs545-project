package edu.mum.service;

import edu.mum.domain.Category;
import edu.mum.domain.Product;
import edu.mum.domain.Seller;

import java.util.List;

public interface ProductService {
    List<Product> getAll();

    Product findById(Long id);

    Product save(Product product);

    void delete(Product product);

    List<Product> getProductsByCategory(Category category);

    List<Product> getProductsBySeller(Seller seller);

    List<Product> getProductsByName(String name);
}
