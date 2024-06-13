package com.swp391.JewelrySalesSystem.request;

import java.util.List;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpsertOrderRequest {
  private String orderCode;
  private Long staffId;
  private String phone;
  private String name;
  private List<ProductOrderRequest> orderList;
  private Float totalPrice;
}
