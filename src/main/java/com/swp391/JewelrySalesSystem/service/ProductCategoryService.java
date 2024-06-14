package com.swp391.JewelrySalesSystem.service;

import com.swp391.JewelrySalesSystem.entity.ProductCategory;
import com.swp391.JewelrySalesSystem.enums.CategoryType;
import java.util.List;

public interface ProductCategoryService {
  List<ProductCategory> getCategoryByType(CategoryType categoryType);

  ProductCategory findById(Long id);

  List<ProductCategory> findAll();
}
