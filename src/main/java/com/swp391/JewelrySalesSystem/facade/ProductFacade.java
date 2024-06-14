package com.swp391.JewelrySalesSystem.facade;

import com.swp391.JewelrySalesSystem.enums.CategoryType;
import com.swp391.JewelrySalesSystem.request.CreateProductRequest;
import com.swp391.JewelrySalesSystem.request.ProductCriteria;
import com.swp391.JewelrySalesSystem.response.*;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface ProductFacade {

  BaseResponse<PaginationResponse<List<ProductResponse>>> findByFilter(ProductCriteria criteria);

  BaseResponse<ProductDetailResponse> findById(Long id);

  BaseResponse<List<CategoryResponse>> getCategoriesByType(CategoryType categoryType);

  BaseResponse<Void> createProduct(CreateProductRequest request, List<MultipartFile> images);

  BaseResponse<Void> deleteProduct(String code);
}
