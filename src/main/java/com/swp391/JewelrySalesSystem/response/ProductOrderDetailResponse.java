package com.swp391.JewelrySalesSystem.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductOrderDetailResponse {
  private Long productId;
  private String productCode;
  private String productName;
  private Float price;
}
