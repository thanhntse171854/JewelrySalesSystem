package com.swp391.JewelrySalesSystem.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRepsone {
  private String orderCode;
  private String phone;
  private String name;
  private Float totalPrice;
}
