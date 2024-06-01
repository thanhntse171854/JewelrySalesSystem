package com.swp391.JewelrySalesSystem.service.impl;

import com.swp391.JewelrySalesSystem.entity.Payment;
import com.swp391.JewelrySalesSystem.repository.PaymentRepository;
import com.swp391.JewelrySalesSystem.service.PaymentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
  private final PaymentRepository paymentRepository;

  @Transactional
  @Override
  public void savePayment(Payment payment) {
    paymentRepository.save(payment);
  }
}
