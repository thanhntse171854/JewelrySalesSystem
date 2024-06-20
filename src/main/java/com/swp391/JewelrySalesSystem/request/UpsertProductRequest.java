package com.swp391.JewelrySalesSystem.request;

import com.swp391.JewelrySalesSystem.enums.Gender;
import com.swp391.JewelrySalesSystem.enums.Size;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpsertProductRequest {
  @NotEmpty(message = "Product code is required")
  private String productCode;

  @NotEmpty(message = "Product name is required")
  private String productName;

  private Long gemCost;

  private Long productionCost;

  @NotEmpty(message = "Gender name is required")
  private Gender gender;

  @NotEmpty(message = "Category name is required")
  private Long categoryId;

  private List<MaterialProductRequest> materialProductRequests;

  private Long gemId;

  private String gemCode;

  private String diamondName;

  private String origin;

  private String color;

  private String clarity;

  private String cut;

  private Float carat;

  private boolean isJewelryDiamond;

  @NotEmpty(message = "Size name is required")
  private Size size;
}
