package com.swp391.JewelrySalesSystem.service;

import com.swp391.JewelrySalesSystem.entity.Product;
import com.swp391.JewelrySalesSystem.request.ProductCriteria;
import java.util.List;
import org.springframework.data.domain.Page;

public interface ProductService {
  Product findByProductIdAndActive(Long id);

  Page<Product> findByFilter(ProductCriteria criteria);

  Product findByProductCode(String code);

  void deactivateProduct(Long productId);

  Product findById(Long id);

  void save(Product product);

  List<Product> findAll();
}
