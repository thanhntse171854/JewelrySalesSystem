package com.swp391.JewelrySalesSystem.facade;

import com.swp391.JewelrySalesSystem.enums.CategoryType;
import com.swp391.JewelrySalesSystem.request.ProductCriteria;
import com.swp391.JewelrySalesSystem.response.*;
import java.util.List;

public interface ProductFacade {

  BaseResponse<PaginationResponse<List<ProductResponse>>> findByFilter(ProductCriteria criteria);

  BaseResponse<ProductDetailResponse> findById(Long id);

  BaseResponse<List<CategoryResponse>> getCategoriesByType(CategoryType categoryType);
}
