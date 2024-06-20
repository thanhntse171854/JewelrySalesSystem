package com.swp391.JewelrySalesSystem.service;

import com.swp391.JewelrySalesSystem.entity.Orders;
import com.swp391.JewelrySalesSystem.request.OrderCriteria;
import java.util.List;
import org.springframework.data.domain.Page;

public interface OrderService {
  void save(Orders orders);

  void delete(Long id);

  List<Orders> getAllHistoryOrder();

  Orders findOrderByPhoneAndCode(String orderCode, String phone);

  Orders findOrderById(Long id);

  Orders findByOrderCode(String code);

  Orders findOrderByCode(String code);

  List<Orders> findOrderBySeller(Long id);

  Page<Orders> findByFilter(OrderCriteria criteria);
}
