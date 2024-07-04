package com.swp391.JewelrySalesSystem.service;

import com.swp391.JewelrySalesSystem.entity.PurchaseOrder;
import java.util.List;

public interface PurchaseService {
  PurchaseOrder findByPurchaseOrderCode(String code);

  List<PurchaseOrder> findAll();

  List<PurchaseOrder> findBySellerId(Long id);

  void save(PurchaseOrder purchaseOrder);

  void deleteById(Long id);
}
