package com.swp391.JewelrySalesSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GemDTO {
  private Long id;
  private String gemCode;
  private String gemName;
  private String origin;
  private String color;
  private String clarity;
  private String cut;
  private Long priceSell;
  private Float weight;
  private Long quantity;
  private Long totalPrice;
}
