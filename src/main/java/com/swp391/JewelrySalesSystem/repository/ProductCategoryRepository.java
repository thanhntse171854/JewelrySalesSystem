package com.swp391.JewelrySalesSystem.repository;

import com.swp391.JewelrySalesSystem.entity.ProductCategory;
import com.swp391.JewelrySalesSystem.enums.CategoryType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
  List<ProductCategory> findProductCategoriesByCategoryType(CategoryType type);
}
