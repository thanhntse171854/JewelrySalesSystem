package com.swp391.JewelrySalesSystem.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TotalAmountMonthResponse {
  private int month;
  private Float totalAmount;
}
