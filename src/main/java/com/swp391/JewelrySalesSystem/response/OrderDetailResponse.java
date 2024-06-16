package com.swp391.JewelrySalesSystem.response;

import com.swp391.JewelrySalesSystem.enums.DeliveryStatus;
import com.swp391.JewelrySalesSystem.enums.PaymentMethod;
import java.util.List;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailResponse {
  private Long orderId;
  private String orderCode;
  private String customerName;
  private String customerPhone;
  private Long dateSell;
  private PaymentMethod paymentMethod;
  private DeliveryStatus deliveryStatus;
  private List<ProductOrderDetailResponse> list;
  private Float totalPrice;
  private Float discount;
}
