package edu.mum.repository;

import edu.mum.domain.Buyer;
import edu.mum.domain.Seller;
import edu.mum.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellerRepository extends CrudRepository<Seller, Long> {
    Seller findSellerByUser(User user);
}
