package com.swp391.JewelrySalesSystem.facade;

import com.swp391.JewelrySalesSystem.request.ChangePasswordRequest;
import com.swp391.JewelrySalesSystem.request.LoginRequest;
import com.swp391.JewelrySalesSystem.request.UpdateProfileRequest;
import com.swp391.JewelrySalesSystem.request.UpdateRoleRequest;
import com.swp391.JewelrySalesSystem.response.BaseResponse;
import com.swp391.JewelrySalesSystem.response.EmployeeResponse;
import com.swp391.JewelrySalesSystem.response.LoginResponse;
import com.swp391.JewelrySalesSystem.response.UserProfileResponse;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface UserFacade {
  BaseResponse<LoginResponse> login(LoginRequest request);

  BaseResponse<UserProfileResponse> getProfile(Long id);

  BaseResponse<UserProfileResponse> getProfileDetail(Long id);

  BaseResponse<UserProfileResponse> updateRole(UpdateRoleRequest request);

  BaseResponse<UserProfileResponse> updateProfileByStaff(UpdateProfileRequest request);

  BaseResponse<List<EmployeeResponse>> findAllStaff();

  BaseResponse<Void> deactivateStaff(Long id);

  BaseResponse<String> updateAvatar(Long id, MultipartFile file);

  void changePassword(ChangePasswordRequest request);

  void logout();
}
