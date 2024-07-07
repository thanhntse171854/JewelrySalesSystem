package com.swp391.JewelrySalesSystem.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class UpsertPriceRequest {
  private Float priceSell;
  private Float priceBuy;
  private Long effectDate;
}
