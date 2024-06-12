package com.swp391.JewelrySalesSystem.specifications;

import com.swp391.JewelrySalesSystem.entity.Gem;
import com.swp391.JewelrySalesSystem.entity.Product;
import com.swp391.JewelrySalesSystem.entity.ProductCategory;
import com.swp391.JewelrySalesSystem.enums.CategoryType;
import com.swp391.JewelrySalesSystem.enums.Gender;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecifications {
  public static Specification<Product> baseSpecification() {
    return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isActive"), true);
  }

  public static Specification<Product> filterByCategory(Long categoryId) {
    return (root, query, criteriaBuilder) -> {
      Join<Product, ProductCategory> categoryJoin = root.join("category", JoinType.INNER);
      return criteriaBuilder.equal(categoryJoin.get("id"), categoryId);
    };
  }

  public static Specification<Product> filterByGender(Gender gender) {
    return (root, query, criteriaBuilder) -> {
      if (gender == null) {
        return null;
      }
      return criteriaBuilder.equal(root.get("gender"), gender);
    };
  }

  public static Specification<Product> filterByCategoryType(CategoryType categoryType) {
    return (root, query, criteriaBuilder) -> {
      Join<Product, ProductCategory> categoryJoin = root.join("category", JoinType.INNER);
      return criteriaBuilder.equal(categoryJoin.get("categoryType"), categoryType);
    };
  }

  public static Specification<Product> filterByProductCode(String productCode) {
    return (root, query, criteriaBuilder) -> {
      if (productCode == null || productCode.trim().isEmpty()) {
        return null;
      }
      return criteriaBuilder.like(root.get("productCode"), "%" + productCode + "%");
    };
  }

  public static Specification<Gem> filterByIsDiamondJewelry() {
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.equal(root.get("isDiamondJewelry"), false);
  }
}
