package com.swp391.JewelrySalesSystem.request;

import java.io.Serializable;
import java.util.List;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PreOrderRequest implements Serializable {
  private String orderCode;
  private Long staffId;
  private String phone;
  private String name;
  private List<ProductOrderRequest> orderList;
  private Float totalPrice;
}
