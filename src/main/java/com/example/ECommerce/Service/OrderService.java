package com.example.ECommerce.Service;

import com.example.ECommerce.Model.Order;
import com.example.ECommerce.Model.OrderItem;
import com.example.ECommerce.Model.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    List<Order> getAllOrders();

    void saveOrder(Order order);

    Order updateOrderStatus(Long id, String status);

    Order createCart(Long userId);

    Order addToCart(Long userId, Long orderId, int quantity);

    Order updateCartQuantity(Long cartItemId, String action);

    Order checkoutCart(Long userId);

    List<Order> getOrderHistory(Long userId);

    Integer getCountCart(Long userId);

    Order getCurOrder(Long orderId);
    Page<Order> getAllOrdersPaginated(int pageNo, int pageSize);

}
