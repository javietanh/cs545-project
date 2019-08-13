package edu.mum.repository;

import edu.mum.domain.Buyer;
import edu.mum.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuyerRepository extends CrudRepository<Buyer, Long> {
    public Buyer findBuyerByUser(User user);
}
