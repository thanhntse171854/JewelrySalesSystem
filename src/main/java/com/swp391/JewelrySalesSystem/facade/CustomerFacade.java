package com.swp391.JewelrySalesSystem.facade;

import com.swp391.JewelrySalesSystem.request.CustomerInfoRequest;
import com.swp391.JewelrySalesSystem.response.BaseResponse;
import com.swp391.JewelrySalesSystem.response.CustomerResponse;
import java.util.List;

public interface CustomerFacade {
  BaseResponse<CustomerResponse> findByPhone(String phone);

  BaseResponse<CustomerResponse> updateInfo(Long id, CustomerInfoRequest request);

  BaseResponse<List<CustomerResponse>> getAllCustomer();
}
