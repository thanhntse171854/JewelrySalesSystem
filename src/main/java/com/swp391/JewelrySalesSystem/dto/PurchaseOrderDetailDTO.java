package com.swp391.JewelrySalesSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class PurchaseOrderDetailDTO {
  private String productCode;
  private String productName;
  private String material;
  private Float weight;
  private String origin;
  private String color;
  private String clarity;
  private Float carat;
  private String price;
  private String size;
}
