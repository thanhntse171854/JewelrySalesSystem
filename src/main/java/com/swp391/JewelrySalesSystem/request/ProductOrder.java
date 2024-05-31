package com.swp391.JewelrySalesSystem.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductOrder {
  private Long productId;
  private Long sizeId;
  private Integer quantity;
}
