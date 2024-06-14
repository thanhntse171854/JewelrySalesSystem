package com.swp391.JewelrySalesSystem.controller;

import com.swp391.JewelrySalesSystem.enums.CategoryType;
import com.swp391.JewelrySalesSystem.facade.ProductFacade;
import com.swp391.JewelrySalesSystem.request.CreateProductRequest;
import com.swp391.JewelrySalesSystem.request.ProductCriteria;
import com.swp391.JewelrySalesSystem.response.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("isAuthenticated()")
  public BaseResponse<List<CategoryResponse>> getCategoryByType(
      @RequestParam CategoryType categoryType) {
    return this.productFacade.getCategoriesByType(categoryType);
  }

  @GetMapping()
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      summary = "Get all product by criteria",
      tags = {"Product APIs"})
  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("isAuthenticated()")
  public BaseResponse<PaginationResponse<List<ProductResponse>>> getProducts(
      @Nullable ProductCriteria criteria) {
    return this.productFacade.findByFilter(criteria);
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      summary = "Get product detail by product id",
      tags = {"Product APIs"})
  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("isAuthenticated()")
  public BaseResponse<ProductDetailResponse> getProductById(@PathVariable("id") Long id) {
    return this.productFacade.findById(id);
  }

  @PostMapping("/create")
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(
      summary = "Create new product",
      tags = {"Product APIs"})
  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("isAuthenticated()")
  public BaseResponse<Void> createNewProduct(
      @RequestBody @Valid CreateProductRequest request,
      @RequestParam("images") List<MultipartFile> images) {
    return this.productFacade.createProduct(request, images);
  }

  @DeleteMapping("/delete/{code}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      summary = "Delete product",
      tags = {"Product APIs"})
  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("isAuthenticated()")
  public BaseResponse<Void> deleteProduct(@PathVariable("code") String code) {
    return this.productFacade.deleteProduct(code);
  }
}
