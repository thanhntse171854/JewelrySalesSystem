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
  private Long staffId;
  private String customer;
  private List<ProductOrder> orderList;
}
