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

    @Query(value = "select i.review from order_item i where i.review_status = 'APPROVED' and i.id = ?", nativeQuery = true)
    List<String> getApprovedReviews(Long itemId);

    @Query(value = "select * from order_item i where i.review is not null", nativeQuery = true)
    List<OrderItem> getOrderItemWithNotNullReviews();

    @Query(value = "select * from order_item i where i.order_status = 'DELIVERED' and i.order_id = ?", nativeQuery = true)
    List<OrderItem> getDeliveredOrderItemsByOrder(Long orderId);
}
