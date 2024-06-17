package com.swp391.JewelrySalesSystem.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrderResponse {
  private String salesStaffName;
  private Long orderId;
  private String orderCode;
  private float totalPrice;
  private Long dateOrder;
  private String customerPhone;
  private String customerName;
}
