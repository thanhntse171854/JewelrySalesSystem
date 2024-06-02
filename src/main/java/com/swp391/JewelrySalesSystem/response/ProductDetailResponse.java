package com.swp391.JewelrySalesSystem.response;

import com.swp391.JewelrySalesSystem.dto.GemDTO;
import com.swp391.JewelrySalesSystem.dto.MaterialDTO;
import com.swp391.JewelrySalesSystem.dto.SizeDTO;
import com.swp391.JewelrySalesSystem.enums.Gender;
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
  private String productName;
  private Long gemCost;
  private Long productionCost;
  private Gender gender;
  private String category;
  private ProductAssetResponse productAsset;
  private List<MaterialDTO> materials;
  private List<SizeDTO> sizeProducts;
  private Float totalPrice;
  private List<GemDTO> gem;
}
