package com.ricardofood.order.repository;

import com.ricardofood.order.enums.Status;
import com.ricardofood.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Order o set o.status = :status where o = :order")
    void updateStatus(Status status, Order order);

    @Query(value = "select o from Order o left join fetch o.items where o.id = :id")
    Order findByIdWithItems(Long id);
}
