package com.swp391.JewelrySalesSystem.service.impl;

import com.swp391.JewelrySalesSystem.entity.PurchaseOrder;
import com.swp391.JewelrySalesSystem.repository.PurchaseRepository;
import com.swp391.JewelrySalesSystem.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {
  private final PurchaseRepository purchaseRepository;

  @Override
  public PurchaseOrder findByPurchaseOrderCode(String code) {
    return purchaseRepository.findByPurchaseOrderCode(code);
  }

  @Override
  public void save(PurchaseOrder purchaseOrder) {
    purchaseRepository.save(purchaseOrder);
  }
}
