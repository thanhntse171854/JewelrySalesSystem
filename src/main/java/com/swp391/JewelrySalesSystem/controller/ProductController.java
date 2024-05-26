package com.swp391.JewelrySalesSystem.controller;

import com.swp391.JewelrySalesSystem.enums.CategoryType;
import com.swp391.JewelrySalesSystem.facade.ProductFacade;
import com.swp391.JewelrySalesSystem.request.ProductCriteria;
import com.swp391.JewelrySalesSystem.response.*;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Nullable;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
  private final ProductFacade productFacade;

  @GetMapping("/categories")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      summary = "Get all category name",
      tags = {"Product APIs"})
  public BaseResponse<List<CategoryResponse>> getCategoryByType(
      @RequestParam CategoryType categoryType) {
    return this.productFacade.getCategoriesByType(categoryType);
  }

  @GetMapping()
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      summary = "Get all product by criteria",
      tags = {"Product APIs"})
  public BaseResponse<PaginationResponse<List<ProductResponse>>> getProducts(
      @Nullable ProductCriteria criteria) {
    return this.productFacade.findByFilter(criteria);
  }

  @GetMapping("/{code}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      summary = "Get product detail by product id",
      tags = {"Product APIs"})
  public BaseResponse<ProductDetailResponse> getProductById(@PathVariable("code") String code) {
    return this.productFacade.findById(code);
  }
}
