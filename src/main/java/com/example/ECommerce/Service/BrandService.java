package com.example.ECommerce.Service;

import com.example.ECommerce.Model.Brand;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BrandService {
    List<Brand> getAllBrands();
    Brand saveBrand(Brand brand);
    boolean deleteBrand(Long id);
    boolean existBrandByName(String brandName);
    Brand getBrandById(Long id);
}
