package edu.mum.repository;

import edu.mum.domain.OrderItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OrderItemRepository extends CrudRepository<OrderItem, Long> {
    @Query("select i from OrderItem i " +
            "inner join Product p on i.product.id = p.id " +
            "inner join Seller s on p.seller.id = s.id " +
            "inner join Order o on i.order.id = o.id " +
            "where s.id = :sellerId")
    List<OrderItem> getOrderItemsBySeller(Long sellerId);

    @Query("select i.review from OrderItem i where i.reviewStatus = 'APPROVED' and i.id = :itemId")
    List<String> getApprovedReviews(Long itemId);
}
