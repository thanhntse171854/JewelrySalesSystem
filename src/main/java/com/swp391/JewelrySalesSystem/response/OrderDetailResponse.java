package com.swp391.JewelrySalesSystem.response;

import com.swp391.JewelrySalesSystem.enums.PaymentMethod;
import java.util.List;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailResponse {
  private String customerName;
  private String customerPhone;
  private Long dateSell;
  private PaymentMethod paymentMethod;
  private List<ProductOrderDetailResponse> list;
}
