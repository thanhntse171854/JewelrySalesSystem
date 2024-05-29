package com.swp391.JewelrySalesSystem.repository;

import com.swp391.JewelrySalesSystem.entity.Product;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository
    extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

  Optional<Product> findByIdAndIsActive(Long id, boolean isActive);

  @Query("SELECT p FROM Product p")
  Page<Product> findByFilter(Pageable pageable);
}
