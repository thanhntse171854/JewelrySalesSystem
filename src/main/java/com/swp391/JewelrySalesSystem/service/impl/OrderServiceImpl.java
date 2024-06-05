package com.swp391.JewelrySalesSystem.service.impl;

import com.swp391.JewelrySalesSystem.entity.Orders;
import com.swp391.JewelrySalesSystem.repository.OrderRepository;
import com.swp391.JewelrySalesSystem.service.OrderService;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;

  @Override
  @Transactional
  public void save(Orders orders) {
    orderRepository.save(orders);
  }

  @Override
  public List<Orders> getAllHistoryOrder() {
    return orderRepository.findAll();
  }

  @Override
  public Orders findOrderByPhoneAndCode(String code, String phone) {
    return orderRepository.findOrderByOrderCodeAndCustomerPhone(code, phone);
  }

  @Override
  public Optional<Orders> findOrderById(Long id) {
    return orderRepository.findById(id);
  }
}
