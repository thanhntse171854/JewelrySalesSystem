package com.swp391.JewelrySalesSystem.service;

import com.swp391.JewelrySalesSystem.entity.Product;
import com.swp391.JewelrySalesSystem.request.ProductCriteria;
import org.springframework.data.domain.Page;

public interface ProductService {
  Product findByProductIdAndActive(Long id);

  Page<Product> findByFilter(ProductCriteria criteria);

  Product findByProductCode(String code);

  Product findById(Long id);

  void deactivateProduct(Long productId);

  void save(Product product);
}
