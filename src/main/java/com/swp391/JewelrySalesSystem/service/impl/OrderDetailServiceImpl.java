package com.swp391.JewelrySalesSystem.service.impl;

import com.swp391.JewelrySalesSystem.repository.OrderDetailRepository;
import com.swp391.JewelrySalesSystem.service.OrderDetailService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {
  private final OrderDetailRepository orderDetailRepository;

  @Override
  public List<Object[]> findCategoryTypeMostOrder() {
    return orderDetailRepository.findCategoryTypeMostOrder();
  }
}
