package com.swp391.JewelrySalesSystem.repository;

import com.swp391.JewelrySalesSystem.entity.SizeProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSizeRepository extends JpaRepository<SizeProduct, Long> {
  SizeProduct findByProductIdAndSizeId(Long productId, Long sizeId);
}
