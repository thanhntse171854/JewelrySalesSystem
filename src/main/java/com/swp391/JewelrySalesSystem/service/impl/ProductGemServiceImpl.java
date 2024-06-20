package com.swp391.JewelrySalesSystem.service.impl;

import com.swp391.JewelrySalesSystem.entity.ProductGem;
import com.swp391.JewelrySalesSystem.repository.ProductGemRepository;
import com.swp391.JewelrySalesSystem.service.ProductGemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductGemServiceImpl implements ProductGemService {
  private final ProductGemRepository productGemRepository;

  @Override
  public void saveProductGem(ProductGem productGem) {
    productGemRepository.save(productGem);
  }
}
