package com.swp391.JewelrySalesSystem.controller;

import com.swp391.JewelrySalesSystem.facade.CustomerFacade;
import com.swp391.JewelrySalesSystem.request.CustomerInfoRequest;
import com.swp391.JewelrySalesSystem.response.BaseResponse;
import com.swp391.JewelrySalesSystem.response.CustomerResponse;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

  private final CustomerFacade customerFacade;

  @PostMapping("/{phone}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      summary = "Find customer by phone",
      tags = {"Customer APIs"})
  //  @SecurityRequirement(name = "Bearer Authentication")
  //  @PreAuthorize("isAuthenticated()")
  public BaseResponse<CustomerResponse> findCustomerByPhone(@PathVariable("phone") String phone) {
    return this.customerFacade.findByPhone(phone);
  }

  @GetMapping()
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      summary = "Get all customer",
      tags = {"Customer APIs"})
  //  @SecurityRequirement(name = "Bearer Authentication")
  //  @PreAuthorize("hasRole('ROLE_MANAGER')")
  public BaseResponse<List<CustomerResponse>> getAllCustomer() {
    return this.customerFacade.getAllCustomer();
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      summary = "Update info customer by id",
      tags = {"Customer APIs"})
  //  @SecurityRequirement(name = "Bearer Authentication")
  //  @PreAuthorize("hasRole('ROLE_MANAGER')")
  public BaseResponse<CustomerResponse> updateInfoCustomer(
      @PathVariable("id") Long id, @RequestBody CustomerInfoRequest request) {
    return this.customerFacade.updateInfo(id, request);
  }
}
