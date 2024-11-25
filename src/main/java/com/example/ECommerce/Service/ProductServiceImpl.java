package com.example.ECommerce.Service;

import com.example.ECommerce.Model.Product;
import com.example.ECommerce.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Iterable<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id){
        return productRepository.findById(id).get();
    }

    @Override
    public void updateProductQuantity(Long id, Integer quantity) {
        Product product = productRepository.findById(id).get();
        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public boolean existsProductByName(String name) {
        return productRepository.existsByName(name);
    }

    @Override
    public Product updateProductById(Long id, Product product) {
        Product currentProduct = null;
        if(productRepository.findById(id).isPresent()){
            currentProduct = productRepository.findById(id).get();
            currentProduct.setName(product.getName());
            currentProduct.setDescription(product.getDescription());
            currentProduct.setPrice(product.getPrice());
            currentProduct.setAvailable(product.getAvailable());
            productRepository.save(currentProduct);
        }
        return currentProduct;
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Iterable<Product> getProductsByCategory(String category) {
        return productRepository.findByAvailableTrueAndCategoryName(category);
    }

    @Override
    public Iterable<Product> getAvailableProducts(boolean available) {
        return productRepository.findByAvailable(available);
    }

    @Override
    public Iterable<Product> getAvailableProductsByCategory(String category){
        return productRepository.findByAvailableTrueAndCategoryName(category);
    }

    @Override
    public Page<Product> getAvailableProducts(int page, int size) {
        return productRepository.findByAvailableTrue(PageRequest.of(page, size));
    }

    @Override
    public List<Product> searchProduct(String ch){
        return productRepository.findByNameContainingIgnoreCaseOrCategory_NameContainingIgnoreCaseOrBrand_NameContainingIgnoreCase(ch, ch, ch);
    }

    @Override
    public Page<Product> getAllActiveProductPagination(int pageNo, int pageSize, String category, String brand, String search) {
        return productRepository.findAllByFilters(
                category.isEmpty() ? null : category,
                brand.isEmpty() ? null : brand,
                search,
                PageRequest.of(pageNo, pageSize)
        );
    }

    @Override
    public Page<Product> getFilteredProducts(int pageNo, int pageSize, String category, String brand, String search, Integer minPrice, Integer maxPrice) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return productRepository.findFilteredProducts(category, brand, search, minPrice, maxPrice, pageable);
    }

}
