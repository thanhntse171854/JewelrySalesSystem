package com.swp391.JewelrySalesSystem.facade;

import com.swp391.JewelrySalesSystem.request.LoginRequest;
import com.swp391.JewelrySalesSystem.response.BaseResponse;
import com.swp391.JewelrySalesSystem.response.LoginResponse;

public interface UserFacade {
  BaseResponse<LoginResponse> login(LoginRequest request);

  void logout();
}
