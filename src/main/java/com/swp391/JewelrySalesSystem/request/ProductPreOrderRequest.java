package com.swp391.JewelrySalesSystem.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductPreOrderRequest {
  private Long productId;
  private String productCode;
  private String productName;
  private String productImage;
  private Long sizeId;
  private Integer quantity;
  private Long price;
}
