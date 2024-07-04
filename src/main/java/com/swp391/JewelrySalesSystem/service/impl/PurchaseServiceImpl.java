package com.swp391.JewelrySalesSystem.service.impl;

import com.swp391.JewelrySalesSystem.entity.PurchaseOrder;
import com.swp391.JewelrySalesSystem.repository.PurchaseRepository;
import com.swp391.JewelrySalesSystem.service.PurchaseService;
import java.util.List;
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
  public List<PurchaseOrder> findAll() {
    return purchaseRepository.findAll();
  }

  @Override
  public void save(PurchaseOrder purchaseOrder) {
    purchaseRepository.save(purchaseOrder);
  }

  @Override
  public void deleteById(Long id) {
    purchaseRepository.deleteById(id);
  }

  @Override
  public List<PurchaseOrder> findBySellerId(Long id) {
    return purchaseRepository.findByUserId(id);
  }
}
