package com.swp391.JewelrySalesSystem.facade;

import com.swp391.JewelrySalesSystem.response.BaseResponse;
import com.swp391.JewelrySalesSystem.response.CustomerResponse;

public interface CustomerFacade {
  BaseResponse<CustomerResponse> findByPhone(String phone);
}
