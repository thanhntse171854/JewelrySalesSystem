package com.swp391.JewelrySalesSystem.controller;

import com.swp391.JewelrySalesSystem.facade.PriceFacade;
import com.swp391.JewelrySalesSystem.response.BaseResponse;
import com.swp391.JewelrySalesSystem.response.GemPriceResponse;
import com.swp391.JewelrySalesSystem.response.MaterialPriceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/prices")
public class PriceController {
  private final PriceFacade priceFacade;

  @GetMapping("/materials")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      summary = "Get all price by material",
      tags = {"Price APIs"})
  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("isAuthenticated()")
  public BaseResponse<List<MaterialPriceResponse>> getMaterialPrice() {
    return this.priceFacade.getMaterialPrice();
  }

  @GetMapping("/gems")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      summary = "Get all price by gems",
      tags = {"Price APIs"})
  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("isAuthenticated()")
  public BaseResponse<List<GemPriceResponse>> getGemPrice() {
    return this.priceFacade.getGemPrice();
  }
}
