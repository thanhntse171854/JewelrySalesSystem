package com.swp391.JewelrySalesSystem.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaterialProductRequest {
  private Long material;
  private Float weight;
}
