package com.swp391.JewelrySalesSystem.dto;

import com.swp391.JewelrySalesSystem.enums.CategoryType;
import com.swp391.JewelrySalesSystem.enums.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class OrderDetailProductDTO {
  private String code;
  private String name;
  private CategoryType categoryType;
  private Float price;
  private Size size;
  private float quantity;
}
