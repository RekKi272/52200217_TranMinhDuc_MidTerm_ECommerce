package com.example.ECommerce.Controller;

import com.example.ECommerce.Model.Product;
import com.example.ECommerce.Model.User;
import com.example.ECommerce.Service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private OrderService orderService;

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

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("brands", brandService.getAllBrands());
        return "index";
    }

    @GetMapping(value = "/signin")
    public String login() {
        return "login";
    }

    // Show the registration page
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    // Handle the registration form submission
    @PostMapping("/saveUser")
    public String registerPost(@ModelAttribute User user,
                               @RequestParam("confirmPassword") String confirmPassword,
                               Model model) {
        // Check if the passwords match
        if (!user.getPassword().equals(confirmPassword)) {
            model.addAttribute("errorMsg", "Passwords do not match. Please try again.");
            return "register";
        }

        // Check if the username or email is already taken
        if (userService.isUsernameTaken(user.getUsername())) {
            model.addAttribute("errorMsg", "Username is already taken.");
            return "register";
        }

        if (userService.isEmailTaken(user.getEmail())) {
            model.addAttribute("errorMsg", "Email is already taken.");
            return "register";
        }

        userService.saveUser(user);
        model.addAttribute("successMsg", "User successfully registered.");

        return "login";
    }
}
