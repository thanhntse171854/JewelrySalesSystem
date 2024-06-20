package com.swp391.JewelrySalesSystem.request;

import com.swp391.JewelrySalesSystem.enums.DeliveryStatus;
import lombok.*;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderCriteria {
  private Integer currentPage;
  private Integer pageSize;
  private String orderCode;
  private DeliveryStatus deliveryStatus;

  @NotNull private Long id;
}
