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
  private Long id;
  private Long gemId;
  private String origin;
  private String color;
  private String clarity;
  private String cut;
  private Float carat;
  private Float gemBuyPrice;
  private Float gemSellPrice;
  private Long effectDate;
}
