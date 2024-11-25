package com.example.ECommerce.Controller;

import com.example.ECommerce.Model.*;
import com.example.ECommerce.Service.*;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.server.PathParam;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommonService commonService;

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


    // Handler for the admin index page
    @GetMapping("/")
    public String adminIndex(Model model) {
        model.addAttribute("brands", brandService.getAllBrands());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/index";
    }

    @GetMapping(value = "/loadAddProduct")
    public String showAddProductForm(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories()); // Pass categories for the dropdown
        model.addAttribute("brands", brandService.getAllBrands());
        return "admin/add_product";  // Return the correct view name
    }

    @GetMapping(value = "/category")
    public String showCategoryForm(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/category";
    }

    @GetMapping(value = "/brand")
    public String showBrandForm(Model model) {
        model.addAttribute("brands", brandService.getAllBrands());
        return "admin/brand";
    }

    @PostMapping(value = "/saveBrand")
    public String saveBrand(@ModelAttribute("brand") Brand brand, HttpSession session) {

        if(brandService.existBrandByName(brand.getName())) {
            session.setAttribute("errorMsg", "Brand name Already Exists");
        }
        else{
            brandService.saveBrand(brand);
            session.setAttribute("successMsg", "Category added successfully");
        }
        return "redirect:/admin/brand";
    }

    @GetMapping(value = "/loadEditBrand/{id}")
    public String showEditBrandForm(@PathVariable Long id, Model model) {
        model.addAttribute("brand", brandService.getBrandById(id));
        return "admin/edit_brand";
    }

    @PostMapping(value="/updateBrand")
    public String updateBrand(@ModelAttribute("brand") Brand brand, HttpSession session) {
        Brand oldBrand = brandService.getBrandById(brand.getId());
        oldBrand.setName(brand.getName());
        brandService.saveBrand(oldBrand);
        session.setAttribute("successMsg", "Category updated successfully");
        return "redirect:/admin/brand";
    }

    @PostMapping(value="/saveCategory")
    public String saveCategory(@ModelAttribute Category category, @RequestParam("file") MultipartFile file, HttpSession session) throws IOException {

        String imageName = file != null ? file.getOriginalFilename() : "default.jpg";
        category.setImage(imageName);

        if(categoryService.existCategory(category.getName())){
            session.setAttribute("errorMsg", "Category name already exists");
        }
        else {
            categoryService.saveCategory(category);

            String path = "src/main/resources/static/images/category_img";  // Your desired path
            commonService.saveImage(path, file);

            session.setAttribute("successMsg", "Category added successfully");
        }
        return "redirect:/admin/category";
    }

    @GetMapping(value = "/deleteBrand/{id}")
    public String deleteBrand(@PathVariable Long id, HttpSession session) {
        boolean deleteBrand = brandService.deleteBrand(id);
        if(deleteBrand){
            session.setAttribute("successMsg", "Category deleted successfully");
        }
        else{
            session.setAttribute("errorMsg", "Category not found");
        }
        return "redirect:/admin/brand";
    }

    @GetMapping("/deleteCategory/{id}")
    public String deleteCategory(@PathVariable Long id, HttpSession session) {
        boolean deleteCategory = categoryService.deleteCategory(id);

        if (deleteCategory) {
            session.setAttribute("successMsg", "category delete success");
        } else {
            session.setAttribute("errorMsg", "something wrong on server");
        }

        return "redirect:/admin/category";
    }

    @GetMapping(value ="/loadEditCategory/{id}")
    public String showEditCategoryForm(@PathVariable Long id, Model model) {
        model.addAttribute("category", categoryService.getCategoryById(id));
        return "admin/edit_category";
    }

    @PostMapping(value = "/updateCategory")
    public String updateCategory(@ModelAttribute Category category, @RequestParam("file") MultipartFile file,
                                 HttpSession session) throws IOException {

        Category oldCategory = categoryService.getCategoryById(category.getId());
        String imageName = file.isEmpty() ? oldCategory.getImage() : file.getOriginalFilename();

        if (!ObjectUtils.isEmpty(category)) {
            oldCategory.setName(category.getName());
            oldCategory.setImage(imageName);
        }

        Category updateCategory = categoryService.saveCategory(oldCategory);

        if (!ObjectUtils.isEmpty(updateCategory)) {

            if (!file.isEmpty()) {
                File saveFile = new ClassPathResource("static/images").getFile();

                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "category_img" + File.separator
                        + file.getOriginalFilename());

                // System.out.println(path);
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            }

            session.setAttribute("successMsg", "Category update success");
        } else {
            session.setAttribute("errorMsg", "something wrong on server");
        }

        return "redirect:/admin/category";
    }

    @GetMapping(value = "/view_product")
    public String showViewProductForm(Model model, @RequestParam(defaultValue = "") String ch) {
        if(ch.isBlank()){
            List<Product> productList = (List<Product>) productService.getAllProducts();
            model.addAttribute("products", productList);
            model.addAttribute("totalElements", productList.size());
        }
        else{
            model.addAttribute("products", productService.searchProduct(ch));
        }
        return "admin/product";
    }


    @PostMapping(value = "/saveProduct")
    public String saveProduct(@ModelAttribute Product product, @RequestParam("file") MultipartFile file, HttpSession session) throws IOException {
        String imageName = file != null ? file.getOriginalFilename() : "default.jpg";
        product.setImage(imageName);

        if(productService.existsProductByName(product.getName())){
            session.setAttribute("errorMsg", "Product name already exists");
        }
        else {
            productService.saveProduct(product);

            String path = "src/main/resources/static/images/product_img";  // Your desired path
            commonService.saveImage(path, file);

            session.setAttribute("successMsg", "Product added successfully");
        }
        return "redirect:/admin/view_product";
    }

    @GetMapping("/editProduct/{id}")
    public String showEditProductForm(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("brands", brandService.getAllBrands());
        return "admin/edit_product";
    }

    @PostMapping(value = "/updateProduct")
    public String updateProduct(@ModelAttribute Product product, @RequestParam("file") MultipartFile file, HttpSession session) throws IOException {
        String ProductImageName = file != null ? file.getOriginalFilename() : "default.jpg";
        product.setImage(ProductImageName);
        if(productService.existsProductByName(product.getName())){
            session.setAttribute("errorMsg", "Product name already exists");
        }
        else {
            productService.saveProduct(product);
            String path = "src/main/resources/static/images/product_img";
            commonService.saveImage(path, file);
            session.setAttribute("successMsg", "Product added successfully");
        }
        return "redirect:/admin/view_product";
    }

    @GetMapping(value = "/loadOrders")
    public String listAllOrders(@RequestParam(defaultValue = "0") int pageNo,
                                @RequestParam(defaultValue = "10") int pageSize,
                                Model model) {
        Page<Order> orderPage = orderService.getAllOrdersPaginated(pageNo, pageSize);
        model.addAttribute("orders", orderPage.getContent());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", orderPage.getTotalPages());
        model.addAttribute("totalElements", orderPage.getTotalElements());
        return "admin/orders";
    }

    @PostMapping("/update-order-status")
    public String updateOrderStatus(@RequestParam("id") Long id, @RequestParam("status") String status) {
        orderService.updateOrderStatus(id, status);
        return "redirect:/admin/loadOrders";
    }


    @GetMapping("/users")
    public String getAllUsers(Model m, @RequestParam Integer type) {
        List<User> users = null;
        if (type == 1) {
            users = userService.getUsersByRole("ROLE_USER");
        } else {
            users = userService.getUsersByRole("ROLE_ADMIN");
        }
        m.addAttribute("userType",type);
        m.addAttribute("users", users);
        return "/admin/users";
    }

    @GetMapping("/updateSts")
    public String updateUserAccountStatus(@RequestParam Boolean status, @RequestParam Long id,@RequestParam Integer type, HttpSession session) {
        Boolean f = userService.updateUserStatus(id, status);
        if (f) {
            session.setAttribute("successMsg", "Account Status Updated");
        } else {
            session.setAttribute("errorMsg", "Something wrong on server");
        }
        return "redirect:/admin/users?type=" + type;
    }

    @GetMapping("/add-admin")
    public String loadAdminAdd() {
        return "/admin/add_admin";
    }

    @PostMapping("/save-admin")
    public String saveAdmin(@ModelAttribute User user, @RequestParam("confirmPassword") String confirmPassword, Model model) {
        // Check if the passwords match
        if (!user.getPassword().equals(confirmPassword)) {
            model.addAttribute("errorMsg", "Passwords do not match. Please try again.");
            return "redirect:/admin/add-admin";
        }

        // Check if the username or email is already taken
        if (userService.isUsernameTaken(user.getUsername())) {
            model.addAttribute("errorMsg", "Username is already taken.");
            return "redirect:/admin/add-admin";
        }

        if (userService.isEmailTaken(user.getEmail())) {
            model.addAttribute("errorMsg", "Email is already taken.");
            return "redirect:/admin/add-admin";
        }
        userService.saveAdmin(user);
        model.addAttribute("successMsg", "User successfully registered.");
        return "redirect:/admin/users?type="+'2';
    }

}
