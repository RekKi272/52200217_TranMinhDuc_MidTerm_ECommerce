package com.example.ECommerce.Service;

import com.example.ECommerce.Model.Brand;
import com.example.ECommerce.Repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandRepository brandRepository;

    @Override
    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    @Override
    public Brand getBrandById(Long id){
        return brandRepository.findById(id).get();
    }

    @Override
    public Brand saveBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    @Override
    public boolean deleteBrand(Long id){
        Brand brand = brandRepository.findById(id).orElse(null);

        if (!ObjectUtils.isEmpty(brand)) {
            brandRepository.delete(brand);
            return true;
        }

        return false;
    }

    @Override
    public boolean existBrandByName(String name) {
        return brandRepository.existsByName(name);
    }
}
