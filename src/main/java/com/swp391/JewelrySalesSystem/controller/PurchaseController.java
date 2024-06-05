package com.swp391.JewelrySalesSystem.controller;

import com.swp391.JewelrySalesSystem.facade.PurchaseFacade;
import com.swp391.JewelrySalesSystem.request.GemFilterRequest;
import com.swp391.JewelrySalesSystem.request.PurchaseOrderRequest;
import com.swp391.JewelrySalesSystem.request.ValidateOrderRequest;
import com.swp391.JewelrySalesSystem.response.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.Nullable;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/purchases")
public class PurchaseController {

  private final PurchaseFacade purchaseFacade;

  @PostMapping("/validate-order")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      summary = "Validate order",
      tags = {"Purchase Order APIs"})
  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("isAuthenticated()")
  public BaseResponse<OrderHistoryResponse> validateOrder(
      @RequestBody ValidateOrderRequest request) {
    return this.purchaseFacade.validateOrder(request);
  }

  @PostMapping("/create")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      summary = "Create purchase order",
      tags = {"Purchase Order APIs"})
  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("isAuthenticated()")
  public BaseResponse<Void> createPurchase(@RequestBody @Nullable PurchaseOrderRequest request) {
    return this.purchaseFacade.createPurchase(request);
  }

  @GetMapping("/prices")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      summary = "Get gem price by filter",
      tags = {"Purchase Order APIs"})
  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("isAuthenticated()")
  public BaseResponse<List<GemPriceResponse>> getGemPrice(
      @RequestParam(required = false) String cut,
      @RequestParam(required = false) Float carat,
      @RequestParam(required = false) String clarity,
      @RequestParam(required = false) String color,
      @RequestParam(required = false) String origin) {

    GemFilterRequest request =
        GemFilterRequest.builder()
            .cut(cut)
            .carat(carat)
            .clarity(clarity)
            .color(color)
            .origin(origin)
            .build();
    return this.purchaseFacade.getGemByFilter(request);
  }
}
