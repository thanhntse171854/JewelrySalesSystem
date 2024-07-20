package com.swp391.JewelrySalesSystem.service;

import com.swp391.JewelrySalesSystem.entity.Orders;
import java.util.List;

public interface OrderService {
  void save(Orders orders);

  void delete(Long id);

  List<Orders> getAllHistoryOrder();

  Orders findOrderByPhoneAndCode(String orderCode, String phone);

  Orders findOrderById(Long id);

  Orders findByOrderCode(String code);

  Orders findOrderByCode(String code);

  List<Orders> findOrderBySeller(Long id);

  List<Orders> findOrderByDate(Long start, Long end);

  List<Object[]> findStaffAndTotalOrderByStaff(Long start, Long end);
}
