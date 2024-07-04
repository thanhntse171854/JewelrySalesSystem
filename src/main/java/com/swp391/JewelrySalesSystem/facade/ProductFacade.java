package com.swp391.JewelrySalesSystem.facade;

import com.swp391.JewelrySalesSystem.enums.CategoryType;
import com.swp391.JewelrySalesSystem.request.ProductCriteria;
import com.swp391.JewelrySalesSystem.request.UpsertProductRequest;
import com.swp391.JewelrySalesSystem.response.*;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface ProductFacade {

  BaseResponse<PaginationResponse<List<ProductResponse>>> findByFilter(ProductCriteria criteria);

  BaseResponse<ProductDetailResponse> findById(Long id);

  BaseResponse<ProductDetailResponse> findByIdForAll(Long id);

  BaseResponse<List<CategoryResponse>> getCategoriesByType(CategoryType categoryType);

  BaseResponse<Void> createProduct(UpsertProductRequest request);

  BaseResponse<Void> deleteProduct(String code);

  BaseResponse<ProductDetailResponse> updateProduct(UpsertProductRequest request);

  BaseResponse<Void> addImages(
      Long id,
      MultipartFile image1,
      MultipartFile image2,
      MultipartFile image3,
      MultipartFile image4);
}
