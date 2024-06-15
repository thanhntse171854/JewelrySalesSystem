package com.swp391.JewelrySalesSystem.service.impl;

import com.swp391.JewelrySalesSystem.entity.Customer;
import com.swp391.JewelrySalesSystem.repository.CustomerRepository;
import com.swp391.JewelrySalesSystem.service.CustomerService;
import jakarta.transaction.Transactional;
import java.util.List;
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

  @Override
  public List<Customer> findAll() {
    return customerRepository.findAll();
  }

  @Transactional
  @Override
  public void save(Customer customer) {
    customerRepository.save(customer);
  }
}
