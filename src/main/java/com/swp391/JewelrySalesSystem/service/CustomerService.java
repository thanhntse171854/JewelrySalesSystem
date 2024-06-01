package com.swp391.JewelrySalesSystem.service;

import com.swp391.JewelrySalesSystem.entity.Customer;

public interface CustomerService {
  Customer findByPhone(String phone);

  void save(Customer customer);
}
