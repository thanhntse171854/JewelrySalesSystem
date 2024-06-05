package com.swp391.JewelrySalesSystem.facade.impl;

import com.swp391.JewelrySalesSystem.facade.CustomerFacade;
import com.swp391.JewelrySalesSystem.response.BaseResponse;
import com.swp391.JewelrySalesSystem.response.CustomerResponse;
import com.swp391.JewelrySalesSystem.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerFacadeImpl implements CustomerFacade {
  private final CustomerService customerService;

  @Override
  public BaseResponse<CustomerResponse> findByPhone(String phone) {
    var customer = customerService.findByPhone(phone);
    return BaseResponse.build(
        CustomerResponse.builder()
            .name(customer.getName())
            .phone(customer.getPhone())
            .address(customer.getAddress())
            .dateOfBirth(customer.getDateOfBirth())
            .percentDiscount(customer.getPercentDiscount())
            .totalAmountPurchased(customer.getTotalAmountPurchased())
            .build(),
        true);
  }
}
