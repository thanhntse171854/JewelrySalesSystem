package com.swp391.JewelrySalesSystem.facade.impl;

import com.swp391.JewelrySalesSystem.entity.Customer;
import com.swp391.JewelrySalesSystem.facade.CustomerFacade;
import com.swp391.JewelrySalesSystem.response.BaseResponse;
import com.swp391.JewelrySalesSystem.response.CustomerResponse;
import com.swp391.JewelrySalesSystem.service.CustomerService;
import java.util.ArrayList;
import java.util.List;
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
            .customerId(customer.getId())
            .name(customer.getName())
            .phone(customer.getPhone())
            .address(customer.getAddress())
            .dateOfBirth(customer.getDateOfBirth())
            .percentDiscount(customer.getPercentDiscount())
            .totalAmountPurchased(customer.getTotalAmountPurchased())
            .build(),
        true);
  }

  @Override
  public BaseResponse<List<CustomerResponse>> getAllCustomer() {
    List<Customer> customer = customerService.findAll();
    List<CustomerResponse> customerResponses = new ArrayList<>();
    for (var cust : customer) {
      customerResponses.add(
          CustomerResponse.builder()
              .customerId(cust.getId())
              .name(cust.getName())
              .phone(cust.getPhone())
              .address(cust.getAddress())
              .dateOfBirth(cust.getDateOfBirth())
              .percentDiscount(cust.getPercentDiscount())
              .totalAmountPurchased(cust.getTotalAmountPurchased())
              .build());
    }
    return BaseResponse.build(customerResponses, true);
  }
}
