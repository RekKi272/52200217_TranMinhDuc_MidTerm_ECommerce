package com.example.ECommerce.Controller;

import com.example.ECommerce.Model.Order;
import com.example.ECommerce.Model.OrderItem;
import com.example.ECommerce.Model.User;
import com.example.ECommerce.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/user")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private OrderItemService orderItemService;

    @ModelAttribute
    public void getUserInformation(Principal p, Model model) {
        if (p != null) {
            String username = p.getName();
            User curUser = userService.getUserByUsername(username);
            if (curUser != null) {
                model.addAttribute("user", curUser);
                Integer countCart = orderService.getCountCart(curUser.getId());
                model.addAttribute("countCart", countCart);
            } else {
                model.addAttribute("user", null);
            }
        } else {
            model.addAttribute("user", null);
        }
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("brands", brandService.getAllBrands());
    }


//    @GetMapping
//    public List<Order> getAllOrders() {
//        return orderService.getAllOrders();
//    }

    @PutMapping("/{id}/status")
    public Order updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
        return orderService.updateOrderStatus(id, status);
    }

    @GetMapping(value = "/cart")
    public String viewCart(Principal p, Model model) {
        if(p != null) {
            String username = p.getName();
            User curUser = userService.getUserByUsername(username);
            Order cart = orderService.createCart(curUser.getId());
            model.addAttribute("cart", cart);
            model.addAttribute("carts", cart.getItems());
            model.addAttribute("totalOrderPrice", cart.getItems().stream().mapToDouble(OrderItem::getPrice).sum());
            return "user/cart";
        }
        else {
            return "redirect:/signin";
        }
    }

    @GetMapping(value = "/addCart")
    public String addToCart(@RequestParam Long uid, @RequestParam Long pid, @RequestParam int quantity) {
        orderService.addToCart(uid, pid, quantity);
        return "redirect:/user/cart";
    }

    @GetMapping("/cartQuantityUpdate")
    public String updateCartQuantity(@RequestParam Long cid, @RequestParam String sy, Principal principal) {
        if (principal != null) {
            // Retrieve the username from the principal object
            String username = principal.getName();

            // Get the user object using the username
            User currentUser = userService.getUserByUsername(username);

            // Get the user ID (uid) from the currentUser object
            Long uid = currentUser.getId();

            // Call the updateCartQuantity method with the current user's uid
            orderService.updateCartQuantity(cid, sy);

            // Redirect to the cart page with the user's ID
            return "redirect:/user/cart?uid=" + uid;
        } else {
            // If the user is not logged in, redirect to login page or error page
            return "redirect:/signin";
        }
    }


    @PostMapping(value = "/checkout")
    public String checkoutCart(@RequestParam Long uid){
        orderService.checkoutCart(uid);
        return "redirect:/user/orders?uid=" + uid;
    }

    @GetMapping("/orders")
    public String orderPage(@RequestParam Long cid, Principal principal, Model model) {
        if (principal != null) {
            // Get the current order using the order ID (cid)
            Order curOrder = orderService.getCurOrder(cid);

            // Initialize totalAmount to 0
            Double totalAmount = 0.0;

            // Iterate over the order items to calculate the total amount
            for (OrderItem item : curOrder.getItems()) {
                totalAmount += item.getPrice();  // Assuming 'item.getPrice()' returns the total price for that item
            }

            // Add the current order and total amount to the model
            model.addAttribute("order", curOrder);
            model.addAttribute("totalAmount", totalAmount);  // Add total amount as an attribute

            // Return the view for the order page
            return "user/order";
        } else {
            // If the user is not logged in, redirect to the login page
            return "redirect:/signin";
        }
    }


    @PostMapping(value = "/save-order")
    public String saveOrder(@RequestParam Long cid, @RequestParam String paymentType, Principal principal, Model model) {
        if (principal != null) {
            String username = principal.getName();
            User currentUser = userService.getUserByUsername(username);

            Order order = orderService.getCurOrder(cid);

            // Set payment type and user details
            order.setPaymentMethod(paymentType);
            order.setUser(currentUser);
            order.setStatus("Pending");
            double totalAmount = 0.0;
            List<OrderItem> orderItems = order.getItems();
            for (OrderItem item : orderItems) {
                productService.updateProductQuantity(item.getProduct().getId(), item.getQuantity());
                totalAmount += item.getPrice() * item.getQuantity();
            }

            order.setTotalAmount(totalAmount);

            orderService.saveOrder(order);

            orderService.checkoutCart(currentUser.getId());

            model.addAttribute("order", order);
            model.addAttribute("successMsg", "Order Successfully");
            return "user/success";
        } else {
            return "redirect:/signin";
        }
    }

    @GetMapping(value = "user-orders")
    public String userOrders(Principal principal, Model model) {
        if (principal != null) {
            String username = principal.getName();
            User curUser = userService.getUserByUsername(username);

            List<Order> ordersList = orderService.getOrderHistory(curUser.getId())
                    .stream()
                    .filter(order -> !order.getStatus().equalsIgnoreCase("Pending"))
                    .toList();

            model.addAttribute("orders", ordersList);
            return "user/my_orders";
        } else {
            return "redirect:/signin";
        }
    }
}
