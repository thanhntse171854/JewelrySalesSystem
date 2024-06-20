package com.swp391.JewelrySalesSystem.repository;

import com.swp391.JewelrySalesSystem.entity.ProductGem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductGemRepository extends JpaRepository<ProductGem, Long> {}
