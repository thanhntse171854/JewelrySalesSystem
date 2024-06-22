package com.swp391.JewelrySalesSystem.response;

import com.swp391.JewelrySalesSystem.dto.GemDTO;
import com.swp391.JewelrySalesSystem.dto.MaterialDTO;
import com.swp391.JewelrySalesSystem.enums.Gender;
import com.swp391.JewelrySalesSystem.enums.Size;
import java.util.List;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailResponse {
  private Long id;
  private String productCode;
  private Long categoryId;
  private String productName;
  private Long gemCost;
  private Long productionCost;
  private Gender gender;
  private String category;
  private Size size;
  private ProductAssetResponse productAsset;
  private List<MaterialDTO> materials;
  private Float totalPrice;
  private List<GemDTO> gem;
}
