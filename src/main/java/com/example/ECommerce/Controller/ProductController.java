package com.example.ECommerce.Controller;

import com.example.ECommerce.Model.Category;
import com.example.ECommerce.Model.Product;
import com.example.ECommerce.Model.User;
import com.example.ECommerce.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private UserService userService;

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


    @GetMapping("/products")
    public String Products(Model model,
                           @RequestParam(value = "category", defaultValue = "") String category,
                           @RequestParam(value = "brand", defaultValue = "") String brand,
                           @RequestParam(value = "ch", defaultValue = "") String search,
                           @RequestParam(value = "minPrice", required = false) Integer minPrice,
                           @RequestParam(value = "maxPrice", required = false) Integer maxPrice,
                           @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
                           @RequestParam(name = "pageSize", defaultValue = "12") Integer pageSize) {
        Page<Product> page = productService.getFilteredProducts(pageNo, pageSize,
                category.isEmpty() ? null : category,
                brand.isEmpty() ? null : brand,
                search.isEmpty() ? null : search,
                minPrice, maxPrice);

        // Populate model with data
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("brands", brandService.getAllBrands());
        model.addAttribute("products", page.getContent());
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalElements", page.getTotalElements());
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("isFirst", page.isFirst());
        model.addAttribute("isLast", page.isLast());
        model.addAttribute("paramValue1", category);
        model.addAttribute("paramValue2", brand);
        model.addAttribute("paramValue3", search);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);

        return "product";
    }



    @GetMapping("/product/{id}")
    public String viewProduct(@PathVariable("id") Long id, Model model, Principal principal) {
        // Fetch the product by its ID
        Product product = productService.getProductById(id);
        // Add the product to the model
        model.addAttribute("product", product);

        // Check if the user is logged in
        if (principal != null) {
            User user = userService.getUserByUsername(principal.getName());
            model.addAttribute("user", user);
        } else {
            model.addAttribute("user", null);
        }

        return "view_product"; // This maps to view_product.html
    }

    public List<Product> getAllProducts() {
        return (List<Product>) productService.getAllProducts();
    }


    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return productService.updateProductById(id, product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProductById(id);
    }
}
