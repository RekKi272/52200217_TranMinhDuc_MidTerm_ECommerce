package com.example.ECommerce.Controller;

import com.example.ECommerce.Model.Category;
import com.example.ECommerce.Model.User;
import com.example.ECommerce.Service.BrandService;
import com.example.ECommerce.Service.CategoryService;
import com.example.ECommerce.Service.OrderService;
import com.example.ECommerce.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

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


    // Display the form for adding a new category
    @GetMapping("/admin/categories/add")
    public String showAddCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "admin/category";
    }

    // Handle the form submission
    @PostMapping("/admin/categories")
    public String addCategory(@ModelAttribute Category category) {
        categoryService.saveCategory(category);
        return "redirect:/admin/categories";  // Redirect to the category list page
    }
}
