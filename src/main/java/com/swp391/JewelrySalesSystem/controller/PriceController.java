package com.swp391.JewelrySalesSystem.controller;

import com.swp391.JewelrySalesSystem.facade.PriceFacade;
import com.swp391.JewelrySalesSystem.request.UpsertPriceRequest;
import com.swp391.JewelrySalesSystem.response.BaseResponse;
import com.swp391.JewelrySalesSystem.response.GemPriceResponse;
import com.swp391.JewelrySalesSystem.response.MaterialPriceResponse;
import io.micrometer.common.lang.Nullable;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
  //  @SecurityRequirement(name = "Bearer Authentication")
  //  @PreAuthorize("isAuthenticated()")
  public BaseResponse<List<MaterialPriceResponse>> getMaterialPrice() {
    return this.priceFacade.getMaterialPrice();
  }

  @GetMapping("/gems")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      summary = "Get all price by gems",
      tags = {"Price APIs"})
  //  @SecurityRequirement(name = "Bearer Authentication")
  //  @PreAuthorize("isAuthenticated()")
  public BaseResponse<List<GemPriceResponse>> getGemPrice() {
    return this.priceFacade.getGemPrice();
  }

  @GetMapping("/materials/all/{id}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      summary = "Get all price of material except effect date",
      tags = {"Price APIs"})
  //  @SecurityRequirement(name = "Bearer Authentication")
  //  @PreAuthorize("isAuthenticated()")
  public BaseResponse<List<MaterialPriceResponse>> getMaterialPriceNotEffectDate(
      @PathVariable("id") Long id) {
    return this.priceFacade.getMaterialNotEffectDate(id);
  }

  @GetMapping("/gems/all/{id}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      summary = "Get all price of gems except effect date",
      tags = {"Price APIs"})
  //  @SecurityRequirement(name = "Bearer Authentication")
  //  @PreAuthorize("isAuthenticated()")
  public BaseResponse<List<GemPriceResponse>> getGemPriceNotEffectDate(
      @PathVariable("id") Long id) {
    return this.priceFacade.getGemNotEffectDate(id);
  }

  @PutMapping("/materials/{id}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      summary = "Update price by gold id day",
      tags = {"Price APIs"})
  //  @SecurityRequirement(name = "Bearer Authentication")
  //  @PreAuthorize("isAuthenticated()")
  public BaseResponse<Void> updateGoldPrice(
      @PathVariable("id") Long id, @RequestBody @Nullable UpsertPriceRequest request) {
    return this.priceFacade.updatePrice(id, request);
  }

  @PutMapping("/gems/{id}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      summary = "Update price by gem id day",
      tags = {"Price APIs"})
  //  @SecurityRequirement(name = "Bearer Authentication")
  //  @PreAuthorize("isAuthenticated()")
  public BaseResponse<Void> updateGemPrice(
      @PathVariable("id") Long id, @RequestBody @Nullable UpsertPriceRequest request) {
    return this.priceFacade.updatePriceGem(id, request);
  }

  @PostMapping("/materials/create/{id}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      summary = "Create material price with effect date",
      tags = {"Price APIs"})
  //  @SecurityRequirement(name = "Bearer Authentication")
  //  @PreAuthorize("isAuthenticated()")
  public BaseResponse<Void> createMaterialPrice(
      @PathVariable("id") Long id, @RequestBody @Nullable UpsertPriceRequest request) {
    return this.priceFacade.createPriceMaterial(id, request);
  }
}
