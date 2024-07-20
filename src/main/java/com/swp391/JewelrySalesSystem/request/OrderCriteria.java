package com.swp391.JewelrySalesSystem.request;

import lombok.*;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderCriteria {
  @NotNull private Long id;
}
