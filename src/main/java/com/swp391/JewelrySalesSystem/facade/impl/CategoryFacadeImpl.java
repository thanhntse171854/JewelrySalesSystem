package com.swp391.JewelrySalesSystem.facade.impl;

import com.swp391.JewelrySalesSystem.entity.ProductCategory;
import com.swp391.JewelrySalesSystem.facade.CategoryFacade;
import com.swp391.JewelrySalesSystem.response.BaseResponse;
import com.swp391.JewelrySalesSystem.response.CategoryResponse;
import com.swp391.JewelrySalesSystem.service.ProductCategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryFacadeImpl implements CategoryFacade {
  private final ProductCategoryService categoryService;

  @Override
  public BaseResponse<List<CategoryResponse>> getAllCategory() {
    List<ProductCategory> list = categoryService.findAll();
    List<CategoryResponse> categoryResponses =
        list.stream()
            .map(
                category ->
                    CategoryResponse.builder()
                        .id(category.getId())
                        .categoryName(category.getCategoryName())
                        .categoryType(category.getCategoryType().toString())
                        .build())
            .toList();
    return BaseResponse.build(categoryResponses, true);
  }
}
