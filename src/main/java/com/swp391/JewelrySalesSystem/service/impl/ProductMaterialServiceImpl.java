package com.swp391.JewelrySalesSystem.service.impl;

import com.swp391.JewelrySalesSystem.repository.ProductMaterialRepository;
import com.swp391.JewelrySalesSystem.service.ProductMaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductMaterialServiceImpl implements ProductMaterialService {
  private final ProductMaterialRepository productMaterialRepository;

  @Override
  public void deleteProductById(Long id) {
    productMaterialRepository.deleteByProductId(id);
  }
}
