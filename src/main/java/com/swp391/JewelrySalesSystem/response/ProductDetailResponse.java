package com.swp391.JewelrySalesSystem.response;

import com.swp391.JewelrySalesSystem.dto.GemDTO;
import com.swp391.JewelrySalesSystem.enums.Gender;
import java.util.List;
import java.util.Map;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailResponse {
  private Long id;
  private String productCode;
  private String productName;
  private Long gemCost;
  private Long productionCost;
  private Gender gender;
  private String category;
  private Map<String, Float> materials;
  private Map<Float, Long> sizeProducts;
  private Float totalPrice;
  private List<GemDTO> gem;
}
