package com.swp391.JewelrySalesSystem.service.impl;

import com.swp391.JewelrySalesSystem.entity.SizeProduct;
import com.swp391.JewelrySalesSystem.repository.ProductSizeRepository;
import com.swp391.JewelrySalesSystem.service.ProductSizeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductSizeImpl implements ProductSizeService {
  private final ProductSizeRepository productSizeRepository;

  @Override
  public SizeProduct findByProductIdAndSizeId(Long productId, Long sizeId) {
    return productSizeRepository.findByProductIdAndSizeId(productId, sizeId);
  }
}
