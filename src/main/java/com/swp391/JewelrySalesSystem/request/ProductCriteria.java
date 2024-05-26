package com.swp391.JewelrySalesSystem.request;

import com.swp391.JewelrySalesSystem.enums.CategoryType;
import com.swp391.JewelrySalesSystem.enums.Gender;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductCriteria extends BaseCriteria {
  private Integer currentPage;
  private Integer pageSize;
  private Gender gender;
  private Long categoryId;
  private CategoryType categoryType;
}
