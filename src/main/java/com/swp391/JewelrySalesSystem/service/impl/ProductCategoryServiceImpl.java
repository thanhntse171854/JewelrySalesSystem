package com.swp391.JewelrySalesSystem.service.impl;

import com.swp391.JewelrySalesSystem.entity.ProductCategory;
import com.swp391.JewelrySalesSystem.enums.CategoryType;
import com.swp391.JewelrySalesSystem.enums.ErrorCode;
import com.swp391.JewelrySalesSystem.exception.CategoryException;
import com.swp391.JewelrySalesSystem.repository.ProductCategoryRepository;
import com.swp391.JewelrySalesSystem.service.ProductCategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductCategoryServiceImpl implements ProductCategoryService {
  private final ProductCategoryRepository productCategoryRepository;

  @Override
  public List<ProductCategory> getCategoryByType(CategoryType categoryType) {
    return productCategoryRepository.findProductCategoriesByCategoryType(categoryType);
  }

  @Override
  public ProductCategory findById(Long id) {
    return productCategoryRepository
        .findById(id)
        .orElseThrow(() -> new CategoryException(ErrorCode.CATEGORY_NOT_FOUND));
  }

  @Override
  public List<ProductCategory> findAll() {
    return productCategoryRepository.findAll();
  }
}
