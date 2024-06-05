package com.swp391.JewelrySalesSystem.service;

import com.swp391.JewelrySalesSystem.entity.Orders;
import java.util.List;
import java.util.Optional;

public interface OrderService {
  void save(Orders orders);

  List<Orders> getAllHistoryOrder();

  Orders findOrderByPhoneAndId(Long orderId, String phone);

  Optional<Orders> findOrderById(Long id);
}
