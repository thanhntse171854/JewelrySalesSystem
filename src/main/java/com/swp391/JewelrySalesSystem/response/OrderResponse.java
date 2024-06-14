package com.swp391.JewelrySalesSystem.response;

import com.swp391.JewelrySalesSystem.enums.DeliveryStatus;
import com.swp391.JewelrySalesSystem.enums.PaymentMethod;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
  private String orderCode;
  private String phone;
  private String name;
  private DeliveryStatus deliveryStatus;
  private PaymentMethod paymentMethod;
  private Float totalPrice;
}
