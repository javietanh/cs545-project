package edu.mum.repository;

import edu.mum.domain.Category;
import edu.mum.domain.Product;
import edu.mum.domain.Seller;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    List<Product> findProductsByCategory(Category category);

    List<Product> findProductsBySeller(Seller seller);
}
