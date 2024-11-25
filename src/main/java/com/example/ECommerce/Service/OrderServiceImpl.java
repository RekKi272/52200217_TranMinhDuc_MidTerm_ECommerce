package com.example.ECommerce.Service;

import com.example.ECommerce.Model.Order;
import com.example.ECommerce.Model.OrderItem;
import com.example.ECommerce.Model.Product;
import com.example.ECommerce.Model.User;
import com.example.ECommerce.Repository.OrderItemRepository;
import com.example.ECommerce.Repository.OrderRepository;
import com.example.ECommerce.Repository.ProductRepository;
import com.example.ECommerce.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductService productService;

    @Override
    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    @Override
    public Order getCurOrder(Long orderId){
        Optional<Order> order = orderRepository.findById(orderId);
        return order.orElse(null);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order updateOrderStatus(Long id, String status) {
        Order curOrder = orderRepository.getOrderById(id);
        curOrder.setStatus(status);
        return orderRepository.save(curOrder);
    }

    @Override
    public Order createCart(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User Not Found"));

        Optional<Order> existingCart = orderRepository.findByUserAndStatus(user, "Pending");

        if (existingCart.isPresent()) {
            return existingCart.get();
        }

        Order newCart = new Order();
        newCart.setUser(user);
        newCart.setStatus("Pending");
        newCart.setTotalAmount(0.0);
        newCart.setOrderDate(LocalDateTime.now());
        return orderRepository.save(newCart);
    }


    @Override
    public Order addToCart(Long userId, Long productId, int quantity) {
        Order cart = createCart(userId);
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Product Not Found"));

        Optional<OrderItem> existingItem = cart.getItems().stream().filter(item -> item.getProduct().getId().equals(productId)).findFirst();

        // Safeguard null check for items
        if (cart.getItems() == null) {
            cart.setItems(new ArrayList<>());
        }

        if(existingItem.isPresent()){
            OrderItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
            item.setPrice(product.getPrice() * item.getQuantity());
            orderItemRepository.save(item);
        }
        else{
            OrderItem newItem = new OrderItem();
            newItem.setOrder(cart);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            newItem.setPrice(product.getPrice() * quantity);
            orderItemRepository.save(newItem);
            cart.getItems().add(newItem);
        }

        return orderRepository.save(cart);
    }

    @Override
    public Integer getCountCart(Long userId) {
        return orderRepository.countByUserId(userId);
    }

    @Override
    public Order updateCartQuantity(Long cartItemId, String action){
        OrderItem cartItem = orderItemRepository.findById(cartItemId).orElseThrow(() -> new IllegalArgumentException("Item Not Found"));
        if(action.equals("in")){
            cartItem.setQuantity(cartItem.getQuantity() + 1);
        } else if (action.equals("de")) {
            if(cartItem.getQuantity() > 1){
                cartItem.setQuantity(cartItem.getQuantity() - 1);
            }
        }
        cartItem.setPrice(cartItem.getProduct().getPrice() * cartItem.getQuantity());
        orderItemRepository.save(cartItem);

        return cartItem.getOrder();
    }

    @Override
    public Order checkoutCart(Long userId){
        Order cart = orderRepository.findByUserAndStatus(userRepository.findById(userId).get(), "Pending").orElseThrow(() -> new IllegalArgumentException("No active cart found"));

        cart.setStatus("In Progress");
        cart.setOrderDate(LocalDateTime.now());
        return orderRepository.save(cart);
    }

    @Override
    public List<Order> getOrderHistory(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User Not Found"));

        return orderRepository.findByUserOrderByOrderDateDesc(user);
    }

    @Override
    public Page<Order> getAllOrdersPaginated(int pageNo, int pageSize){
        return orderRepository.findAll(PageRequest.of(pageNo, pageSize));
    }
}
