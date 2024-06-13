package com.swp391.JewelrySalesSystem.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class CustomerResponse {
  private String name;
  private String phone;
  private String address;
  private Long dateOfBirth;
  private Float percentDiscount;
  private Float totalAmountPurchased;
}
