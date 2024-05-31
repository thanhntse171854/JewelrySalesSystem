package com.swp391.JewelrySalesSystem.service.impl;

import com.swp391.JewelrySalesSystem.entity.Payment;
import com.swp391.JewelrySalesSystem.repository.PaymentRepository;
import com.swp391.JewelrySalesSystem.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
  private final PaymentRepository paymentRepository;

  @Override
  public void savePayment(Payment payment) {
    paymentRepository.save(payment);
  }
}
