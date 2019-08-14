package edu.mum.service.impl;

import edu.mum.domain.Category;
import edu.mum.domain.Product;
import edu.mum.domain.Seller;
import edu.mum.repository.ProductRepository;
import edu.mum.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAll() {
        return (List<Product>) productRepository.findAll();
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).get();
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void delete(Product product) {
        productRepository.delete(product);
    }

    @Override
    public List<Product> getProductsByCategory(Category category) {
        return productRepository.findProductsByCategory(category);
    }

    @Override
    public List<Product> getProductsBySeller(Seller seller) {
        return productRepository.findProductsBySeller(seller);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findProductsByName(name);
    }
}
