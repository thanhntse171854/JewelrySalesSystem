package com.swp391.JewelrySalesSystem.response;

import java.time.LocalDate;
import lombok.*;

@Setter
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TotalAmountDayResponse {
  private LocalDate date;
  private Float totalAmount;
}
