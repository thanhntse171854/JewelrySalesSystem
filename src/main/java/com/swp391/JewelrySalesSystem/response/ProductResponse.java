package com.swp391.JewelrySalesSystem.response;

import com.swp391.JewelrySalesSystem.enums.Size;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
  private Long productId;
  private String productCode;
  private String productName;
  private float productPrice;
  private String productImage;
  private Size size;
}
