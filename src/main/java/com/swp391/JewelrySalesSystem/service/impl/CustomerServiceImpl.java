package com.swp391.JewelrySalesSystem.service.impl;

import com.swp391.JewelrySalesSystem.entity.Customer;
import com.swp391.JewelrySalesSystem.repository.CustomerRepository;
import com.swp391.JewelrySalesSystem.service.CustomerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
  private final CustomerRepository customerRepository;

  @Override
  public Customer findByPhone(String phone) {
    return customerRepository.findByPhone(phone);
  }

  @Transactional
  @Override
  public void save(Customer customer) {
    customerRepository.save(customer);
  }
}
