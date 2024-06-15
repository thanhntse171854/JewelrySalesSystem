package com.swp391.JewelrySalesSystem.service;

import com.swp391.JewelrySalesSystem.entity.PurchaseOrder;

public interface PurchaseService {
  PurchaseOrder findByPurchaseOrderCode(String code);

  void save(PurchaseOrder purchaseOrder);
}
