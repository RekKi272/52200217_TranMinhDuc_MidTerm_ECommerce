package com.example.ECommerce.Repository;


import com.example.ECommerce.Model.Brand;
import com.example.ECommerce.Model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ECommerce.Model.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Fetch all available products by category
    Iterable<Product> findAllByCategory(Category category);

    // Fetch all available products
    Iterable<Product> findByAvailable(boolean available);

    // Fetch all available products
    Page<Product> findByAvailableTrue(Pageable pageable);

    // Fetch all available products by category
    Iterable<Product> findByAvailableTrueAndCategoryName(String categoryName);

    boolean existsByName(String name);
    List<Product> findByNameContainingIgnoreCaseOrCategory_NameContainingIgnoreCaseOrBrand_NameContainingIgnoreCase(
            String nameKeyword, String categoryKeyword, String brandKeyword);

    @Query("SELECT p FROM Product p WHERE " +
            "(:category IS NULL OR p.category.name = :category) AND " +
            "(:brand IS NULL OR p.brand.name = :brand) AND " +
            "(LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%'))) AND " +
            "p.available = true")
    Page<Product> findAllByFilters(String category, String brand, String search, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE " +
            "p.available = true AND " +
            "(:category IS NULL OR :category = '' OR p.category.name = :category) AND " +
            "(:brand IS NULL OR :brand = '' OR p.brand.name = :brand) AND " +
            "(:search IS NULL OR :search = '' OR LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%'))) AND " +
            "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR p.price <= :maxPrice)")
    Page<Product> findFilteredProducts(@Param("category") String category,
                                       @Param("brand") String brand,
                                       @Param("search") String search,
                                       @Param("minPrice") Integer minPrice,
                                       @Param("maxPrice") Integer maxPrice,
                                       Pageable pageable);

}
