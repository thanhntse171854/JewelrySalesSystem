package com.swp391.JewelrySalesSystem.service;

import com.swp391.JewelrySalesSystem.entity.Product;
import com.swp391.JewelrySalesSystem.request.ProductCriteria;
import org.springframework.data.domain.Page;

public interface ProductService {
  Product findByProductIdAndActive(Long id);

  Page<Product> findByFilter(ProductCriteria criteria);
}
