package com.swp391.JewelrySalesSystem.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaterialPriceResponse {
  private Long materialId;
  private String materialName;
  private Long materialBuyPrice;
  private Long materialSellPrice;
  private Long effectDate;
}
