package com.swp391.JewelrySalesSystem.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class PurchaseOrderRequest {
  private String purchaseOrderCode;
  private Long staffId;
  private String customerName;
  private String address;
  private Long birthday;
  private String phone;
  private boolean isProductStore;
  private List<PurchaseOrderDetailRequest> list;
  private Float totalPrice;
}
