package com.example.ECommerce.Repository;

import com.example.ECommerce.Model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    boolean existsByName(String brandName);
}
