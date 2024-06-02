package com.swp391.JewelrySalesSystem.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class PurchaseOrderDetailRequest {
  private String name;
  private Long productId;
  private Long materialId;
  private Float weight;
  private String origin;
  private String color;
  private String clarity;
  private String cut;
  private Float carat;
  private Float price;
}
