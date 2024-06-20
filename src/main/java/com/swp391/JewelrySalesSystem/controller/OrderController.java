package com.swp391.JewelrySalesSystem.controller;

import com.swp391.JewelrySalesSystem.facade.OrderFacade;
import com.swp391.JewelrySalesSystem.request.OrderCriteria;
import com.swp391.JewelrySalesSystem.request.PaymentRequest;
import com.swp391.JewelrySalesSystem.request.UpsertOrderRequest;
import com.swp391.JewelrySalesSystem.response.*;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Nullable;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
  private final OrderFacade orderFacade;

  @PostMapping("/order")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      summary = "Submit order by seller",
      tags = {"Sell Order APIs"})
  //  @SecurityRequirement(name = "Bearer Authentication")
  //  @PreAuthorize("isAuthenticated()")
  public BaseResponse<Void> order(@RequestBody UpsertOrderRequest request) {
    return this.orderFacade.orderProduct(request);
  }

  @PostMapping("/export-pdf/{code}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      summary = "Generate PDF",
      tags = {"Sell Order APIs"})
  //  @SecurityRequirement(name = "Bearer Authentication")
  //  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<byte[]> generatePDF(@PathVariable("code") String code) {
    return this.orderFacade.generateDocument(code);
  }

  @PutMapping("/order/update")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      summary = "Update order by seller",
      tags = {"Sell Order APIs"})
  //  @SecurityRequirement(name = "Bearer Authentication")
  //  @PreAuthorize("isAuthenticated()")
  public BaseResponse<Void> updateOrder(@RequestBody UpsertOrderRequest request) {
    return this.orderFacade.orderProduct(request);
  }

  @DeleteMapping("/order/delete/{code}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      summary = "Delete order by seller or cashier",
      tags = {"Sell Order APIs"})
  //  @SecurityRequirement(name = "Bearer Authentication")
  //  @PreAuthorize("isAuthenticated()")
  public BaseResponse<Void> deletePreOrderByKey(@PathVariable("code") String code) {
    return this.orderFacade.deleteOderByKey(code);
  }

  @PostMapping("/payment")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      summary = "Payment order",
      tags = {"Sell Order APIs"})
  //  @SecurityRequirement(name = "Bearer Authentication")
  //  @PreAuthorize("hasRole('ROLE_CASHIER_STAFF')")
  public BaseResponse<Void> payment(@RequestBody PaymentRequest request) {
    return this.orderFacade.payment(request);
  }

  @PostMapping("/delivery/{orderCode}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      summary = "Delivery order by seller",
      tags = {"Sell Order APIs"})
  //  @SecurityRequirement(name = "Bearer Authentication")
  //  @PreAuthorize("hasRole('ROLE_SALES_STAFF')")
  public BaseResponse<Void> updateStatusDelivery(@PathVariable("orderCode") String code) {
    return this.orderFacade.updateStatusDelivery(code);
  }

  @GetMapping("/order/{staffId}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      summary = "Get order by seller ID",
      tags = {"Sell Order APIs"})
  //  @SecurityRequirement(name = "Bearer Authentication")
  //  @PreAuthorize("isAuthenticated()")
  public BaseResponse<PaginationResponse<List<OrderResponse>>> getOrderBySeller(
      @Nullable OrderCriteria orderCriteria) {

    return this.orderFacade.getOrderProductBySeller(orderCriteria);
  }

  @GetMapping("/detail/{orderCode}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      summary = "Get order detail by orderCode",
      tags = {"Sell Order APIs"})
  //  @SecurityRequirement(name = "Bearer Authentication")
  //  @PreAuthorize("isAuthenticated()")
  public BaseResponse<OrderDetailResponse> getOrderDetail(@PathVariable("orderCode") String code) {
    return this.orderFacade.getOrderDetail(code);
  }

  @GetMapping("/order/history")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      summary = "Get all history order",
      tags = {"Sell Order APIs"})
  //  @SecurityRequirement(name = "Bearer Authentication")
  //  @PreAuthorize("isAuthenticated()")
  public BaseResponse<List<OrderHistoryResponse>> getAllHistoryOrder() {
    return this.orderFacade.getAllHistoryOrder();
  }
}
