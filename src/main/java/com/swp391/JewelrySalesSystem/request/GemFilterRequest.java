package com.swp391.JewelrySalesSystem.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class GemFilterRequest {
  private String origin;
  private String color;
  private String clarity;
  private String cut;
  private Float carat;
}
