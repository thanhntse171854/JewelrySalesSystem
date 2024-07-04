package com.swp391.JewelrySalesSystem.repository;

import com.swp391.JewelrySalesSystem.entity.ProductMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProductMaterialRepository extends JpaRepository<ProductMaterial, Long> {
  @Transactional
  @Modifying
  @Query(
      value = "delete from product_materials pm where pm.product_id = :productId",
      nativeQuery = true)
  void deleteByProductId(@Param("productId") Long productId);
}
