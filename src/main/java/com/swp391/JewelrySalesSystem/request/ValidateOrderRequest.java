package com.swp391.JewelrySalesSystem.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ValidateOrderRequest {
  private String phone;
  private Long orderId;
}
