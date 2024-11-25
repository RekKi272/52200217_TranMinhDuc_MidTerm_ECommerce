package com.example.ECommerce.Service;

import com.example.ECommerce.Model.Product;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    Iterable<Product> getAllProducts();
    Product getProductById(Long id);
    void deleteProductById(Long id);
    boolean existsProductByName(String productName);
    Product updateProductById(Long id,Product product);
    Product saveProduct(Product product);
    Iterable<Product> getProductsByCategory(String category);
    Iterable<Product> getAvailableProducts(boolean available);
    Iterable<Product> getAvailableProductsByCategory(String category);
    Page<Product> getAvailableProducts(int page, int size);
    List<Product> searchProduct(String ch);
    Page<Product> getAllActiveProductPagination(int pageNo, int pageSize, String category, String brand, String search);
    void updateProductQuantity(Long id, Integer quantity);
    List<String> getAllProductColors();
    Page<Product> getFilteredProducts(int pageNo, int pageSize, String category, String brand, String search, String color, Integer minPrice, Integer maxPrice);
}
