package com.swp391.JewelrySalesSystem.controller;

import com.swp391.JewelrySalesSystem.facade.UserFacade;
import com.swp391.JewelrySalesSystem.request.LoginRequest;
import com.swp391.JewelrySalesSystem.request.UpdateProfileRequest;
import com.swp391.JewelrySalesSystem.request.UpdateRoleRequest;
import com.swp391.JewelrySalesSystem.response.BaseResponse;
import com.swp391.JewelrySalesSystem.response.EmployeeResponse;
import com.swp391.JewelrySalesSystem.response.LoginResponse;
import com.swp391.JewelrySalesSystem.response.UserProfileResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

  @GetMapping("/profile/{id}")
  @ResponseStatus(HttpStatus.OK)
  @SecurityRequirement(name = "Bearer Authentication")
  @Operation(
      tags = {"USER APIs"},
      summary = "Get profile of Staff")
  @PreAuthorize("isAuthenticated()")
  public BaseResponse<UserProfileResponse> getProfile(@PathVariable("id") Long id) {
    return this.userFacade.getProfile(id);
  }

  @PostMapping("/update/{id}")
  @ResponseStatus(HttpStatus.OK)
  @SecurityRequirement(name = "Bearer Authentication")
  @Operation(
      tags = {"USER APIs"},
      summary = "Update profile by Staff")
  @PreAuthorize("isAuthenticated()")
  public BaseResponse<UserProfileResponse> updateProfile(
      @RequestBody @Nullable UpdateProfileRequest request) {
    return this.userFacade.updateProfileByStaff(request);
  }

  @PostMapping(value = "/update-avatar/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      tags = {"USER APIs"},
      summary = "Update role by Admin")
  @SecurityRequirement(name = "Bearer Authentication")
  public BaseResponse<String> updateAvatar(
      @PathVariable("id") Long id, @RequestPart MultipartFile file) {
    return this.userFacade.updateAvatar(id, file);
  }

  @GetMapping("/staff")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      tags = {"USER APIs"},
      summary = "Get all staff")
  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("hasRole('ROLE_MANAGER') && hasRole('ROLE_ADMIN')")
  public BaseResponse<List<EmployeeResponse>> getStaff() {
    return this.userFacade.findAllStaff();
  }

  @DeleteMapping("/delete/{id}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      tags = {"USER APIs"},
      summary = "Delete staff by Id")
  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("hasRole('ROLE_MANAGER') && hasRole('ROLE_ADMIN')")
  public BaseResponse<Void> deleteStaff(@PathVariable("id") Long id) {
    return this.userFacade.deactivateStaff(id);
  }

  @GetMapping("/staff/{id}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      tags = {"USER APIs"},
      summary = "Get profile saff by admin or manager")
  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("hasRole('ROLE_MANAGER') && hasRole('ROLE_ADMIN')")
  public BaseResponse<UserProfileResponse> getProfileDetail(@RequestParam("id") Long id) {
    return this.userFacade.getProfileDetail(id);
  }

  @PutMapping("/staff/change-role")
  @ResponseStatus(HttpStatus.OK)
  @Operation(
      tags = {"USER APIs"},
      summary = "Update role by Admin")
  @SecurityRequirement(name = "Bearer Authentication")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public BaseResponse<UserProfileResponse> updateRole(@RequestBody UpdateRoleRequest request) {
    return this.userFacade.updateRole(request);
  }
}
