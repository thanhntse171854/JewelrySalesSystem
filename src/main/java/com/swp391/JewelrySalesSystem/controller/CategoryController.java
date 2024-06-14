package com.swp391.JewelrySalesSystem.controller;

import com.swp391.JewelrySalesSystem.facade.CategoryFacade;
import com.swp391.JewelrySalesSystem.response.BaseResponse;
import com.swp391.JewelrySalesSystem.response.CategoryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
  private final CategoryFacade categoryFacade;

  @GetMapping("")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      summary = "Get all category",
      tags = {"Category APIs"})
  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("isAuthenticated()")
  public BaseResponse<List<CategoryResponse>> findAll() {
    return this.categoryFacade.getAllCategory();
  }
}
