package com.swp391.JewelrySalesSystem.request;

import com.swp391.JewelrySalesSystem.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
  private String orderCode;
  private PaymentMethod paymentMethod;
  private Float amount;
  private Long orderId;
  private String customerPhone;
}
