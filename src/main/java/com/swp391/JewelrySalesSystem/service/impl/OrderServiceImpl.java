package com.swp391.JewelrySalesSystem.service.impl;

import com.swp391.JewelrySalesSystem.entity.Orders;
import com.swp391.JewelrySalesSystem.enums.DeliveryStatus;
import com.swp391.JewelrySalesSystem.enums.ErrorCode;
import com.swp391.JewelrySalesSystem.enums.PaymentMethod;
import com.swp391.JewelrySalesSystem.exception.OrderExcetpion;
import com.swp391.JewelrySalesSystem.repository.OrderRepository;
import com.swp391.JewelrySalesSystem.request.OrderCriteria;
import com.swp391.JewelrySalesSystem.service.OrderService;
import com.swp391.JewelrySalesSystem.specifications.OrderSpecifications;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
  public void delete(Long id) {
    orderRepository.deleteById(id);
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
  public Orders findOrderById(Long id) {
    return orderRepository
        .findById(id)
        .orElseThrow(() -> new OrderExcetpion(ErrorCode.ORDER_NOT_FOUND));
  }

  @Override
  public Orders findByOrderCode(String code) {
    return orderRepository
        .findByOrderCode(code)
        .orElseThrow(() -> new OrderExcetpion(ErrorCode.ORDER_NOT_FOUND));
  }

  @Override
  public Orders findOrderByCode(String code) {
    return orderRepository
        .findByOrderCode(code)
        .orElse(
            Orders.builder()
                .deliveryStatus(DeliveryStatus.PENDING)
                .paymentMethod(PaymentMethod.NONE)
                .build());
  }

  @Override
  public List<Orders> findOrderBySeller(Long id) {
    return orderRepository.findByUserId(id);
  }

  @Override
  public Page<Orders> findByFilter(OrderCriteria criteria) {
    int currentPage = (criteria.getCurrentPage() == null) ? 1 : criteria.getCurrentPage();
    int pageSize = (criteria.getPageSize() == null) ? 10 : criteria.getPageSize();
    Pageable pageable = PageRequest.of(Math.max(currentPage - 1, 0), pageSize);
    Specification<Orders> specification = OrderSpecifications.baseSpecification(criteria.getId());
    if (criteria.getOrderCode() != null) {
      specification =
          specification.and(OrderSpecifications.filterByOrderCode(criteria.getOrderCode()));
    }

    if (criteria.getDeliveryStatus() != null) {
      specification =
          specification.and(
              OrderSpecifications.filterByDeliveryStatus(criteria.getDeliveryStatus()));
    }

    return orderRepository.findAll(specification, pageable);
  }
}
