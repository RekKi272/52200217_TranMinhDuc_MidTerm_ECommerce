package com.example.ECommerce.Repository;

import com.example.ECommerce.Model.Order;
import com.example.ECommerce.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Order getOrderById(long id);

    Optional<Order> findByUserAndStatus(User user, String status);

    List<Order> findByUserOrderByOrderDateDesc(User user);

    Integer countByUserId(long id);
}
