package com.swp391.JewelrySalesSystem.service;

import com.swp391.JewelrySalesSystem.entity.Payment;

public interface PaymentService {
  void savePayment(Payment payment);

  Payment findByOrderId(Long id);

  Payment findByPurchaseId(Long id);
}
