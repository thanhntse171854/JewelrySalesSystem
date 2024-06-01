package com.swp391.JewelrySalesSystem.service;

import com.swp391.JewelrySalesSystem.entity.SizeProduct;

public interface ProductSizeService {
  SizeProduct findByProductIdAndSizeId(Long productId, Long sizeId);
}
