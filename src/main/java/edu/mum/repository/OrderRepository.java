package edu.mum.repository;

import edu.mum.domain.Order;
import edu.mum.domain.OrderItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {

}
