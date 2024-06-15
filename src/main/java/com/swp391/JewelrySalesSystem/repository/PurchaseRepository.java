package com.swp391.JewelrySalesSystem.repository;

import com.swp391.JewelrySalesSystem.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends JpaRepository<PurchaseOrder, Long> {
  PurchaseOrder findByPurchaseOrderCode(String code);
}
