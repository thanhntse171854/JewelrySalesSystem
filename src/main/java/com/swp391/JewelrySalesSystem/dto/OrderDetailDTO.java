package com.swp391.JewelrySalesSystem.dto;

import com.swp391.JewelrySalesSystem.enums.PaymentMethod;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class OrderDetailDTO {
  private String orderCode;
  private String customerName;
  private String customerAddress;
  private String customerPhone;
  private Float discount;
  private String totalAmount;
  private PaymentMethod paymentMethod;
  private List<OrderDetailProductDTO> list;
  private String createAt;
  private String warrantyTo;
}
