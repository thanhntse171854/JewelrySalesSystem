package com.swp391.JewelrySalesSystem.controller;

import com.swp391.JewelrySalesSystem.facade.OrderFacade;
import com.swp391.JewelrySalesSystem.request.OrderRequest;
import com.swp391.JewelrySalesSystem.request.PreOrderRequest;
import com.swp391.JewelrySalesSystem.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
  private final OrderFacade orderFacade;

  @PostMapping("/order-product")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      summary = "Create new order",
      tags = {"Order APIs"})
  public BaseResponse<Void> createOrder(@RequestBody OrderRequest request) {
    return this.orderFacade.orderProduct(request);
  }

  @PostMapping("/pre-order-product")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      summary = "Submit pre order",
      tags = {"Order APIs"})
  public BaseResponse<Void> preOrder(@RequestBody PreOrderRequest request) {
    return this.orderFacade.preOrderProduct(request);
  }

  @PostMapping("/get-pre-order-product/{key}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      summary = "Get pre order by Key",
      tags = {"Order APIs"})
  public BaseResponse<PreOrderRequest> getPreOrder(@PathVariable("key") String key) {
    return this.orderFacade.getPreOrderProduct(key);
  }

  @PutMapping("/update-pre-order")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      summary = "Update pre order",
      tags = {"Order APIs"})
  public BaseResponse<Void> updatePreOrder(
      @RequestParam String key, @RequestBody PreOrderRequest updatedRequest) {
    return this.orderFacade.updatePreOrderProduct(key, updatedRequest);
  }

  @GetMapping("/get-key-pre-order")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
          summary = "Get key pre order",
          tags = {"Order APIs"})
  public BaseResponse<List<String>> updatePreOrder() {
    return this.orderFacade.getAllKeyPreOrder();
  }
}
