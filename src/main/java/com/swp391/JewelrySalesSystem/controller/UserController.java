package com.swp391.JewelrySalesSystem.controller;

import com.swp391.JewelrySalesSystem.facade.UserFacade;
import com.swp391.JewelrySalesSystem.request.LoginRequest;
import com.swp391.JewelrySalesSystem.response.BaseResponse;
import com.swp391.JewelrySalesSystem.response.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

  private final UserFacade userFacade;

  @PostMapping("/login")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      tags = {"USER APIs"},
      summary = "USER Login")
  public BaseResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
    return this.userFacade.login(request);
  }

  @PostMapping("/logout")
  @ResponseStatus(HttpStatus.OK)
  @SecurityRequirement(name = "Bearer Authentication")
  @Operation(
      tags = {"USER APIs"},
      summary = "User logout")
  @PreAuthorize("isAuthenticated()")
  public BaseResponse<Void> logout() {
    this.userFacade.logout();
    return BaseResponse.ok();
  }
}
