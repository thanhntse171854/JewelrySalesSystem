package com.swp391.JewelrySalesSystem.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@Builder
public class PurchaseOrderDetailResponse {
  private Long orderId;
  private String orderCode;
  private String customerName;
  private String customerPhone;
  private String customerAddress;
  private Long birthday;
  private Float totalAmount;
}
