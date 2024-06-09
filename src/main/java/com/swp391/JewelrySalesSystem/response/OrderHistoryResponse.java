package com.swp391.JewelrySalesSystem.response;

import com.swp391.JewelrySalesSystem.enums.DeliveryStatus;
import com.swp391.JewelrySalesSystem.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderHistoryResponse {
  private String salesStaffName;
  private Long orderId;
  private String orderCode;
  private float totalPrice;
  private Long dateOrder;
  private PaymentMethod paymentMethod;
  private DeliveryStatus deliveryStatus;
}
