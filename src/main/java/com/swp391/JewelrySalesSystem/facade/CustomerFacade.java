package com.swp391.JewelrySalesSystem.facade;

import com.swp391.JewelrySalesSystem.response.BaseResponse;
import com.swp391.JewelrySalesSystem.response.CustomerResponse;
import java.util.List;

public interface CustomerFacade {
  BaseResponse<CustomerResponse> findByPhone(String phone);

  BaseResponse<List<CustomerResponse>> getAllCustomer();
}
