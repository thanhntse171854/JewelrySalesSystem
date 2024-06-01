package com.swp391.JewelrySalesSystem.service;

import com.swp391.JewelrySalesSystem.entity.Orders;
import java.util.List;

public interface OrderService {
  void save(Orders orders);

  List<Orders> getAllHistoryOrder();
}
