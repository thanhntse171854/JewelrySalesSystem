package com.swp391.JewelrySalesSystem.repository;

import com.swp391.JewelrySalesSystem.entity.ProductAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductAssetRepository extends JpaRepository<ProductAsset, Long> {}
