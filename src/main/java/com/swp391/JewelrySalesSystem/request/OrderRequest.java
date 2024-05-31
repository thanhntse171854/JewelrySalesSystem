package com.swp391.JewelrySalesSystem.request;

import com.swp391.JewelrySalesSystem.enums.PaymentMethod;
import java.util.List;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
  private String keyProOrder;
  private Long staffId;
  private String phone;
  private String name;
  private List<ProductOrder> orderList;
  private PaymentMethod paymentMethod;
  private Float discount;
  private Float totalPrice;
}
