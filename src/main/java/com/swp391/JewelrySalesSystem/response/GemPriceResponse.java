package com.swp391.JewelrySalesSystem.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GemPriceResponse {
  private Long gemId;
  private String origin;
  private String color;
  private String clarity;
  private String cut;
  private Float carat;
  private Long gemBuyPrice;
  private Long gemSellPrice;
  private Long effectDate;
}
