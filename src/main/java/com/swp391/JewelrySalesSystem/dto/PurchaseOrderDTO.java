package com.swp391.JewelrySalesSystem.dto;

import com.swp391.JewelrySalesSystem.enums.PaymentMethod;
import java.util.List;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class PurchaseOrderDTO {
  private String orderCode;
  private String customerName;
  private String customerAddress;
  private String customerPhone;
  private String totalPrice;
  private PaymentMethod paymentMethod;
  private List<PurchaseOrderDetailDTO> list;
  private String createAt;
}
