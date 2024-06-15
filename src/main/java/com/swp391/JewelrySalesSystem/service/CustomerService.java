package com.swp391.JewelrySalesSystem.service;

import com.swp391.JewelrySalesSystem.entity.Customer;
import java.util.List;

public interface CustomerService {
  Customer findByPhone(String phone);

  List<Customer> findAll();

  void save(Customer customer);
}
