package com.swp391.JewelrySalesSystem.repository;

import com.swp391.JewelrySalesSystem.entity.Orders;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
  @Query(
      value =
          "select o.* from orders o join customers c on c.id = o.customer_id where  c.phone = :phone  and o.order_code = :code ",
      nativeQuery = true)
  Orders findOrderByOrderCodeAndCustomerPhone(
      @Param("code") String code, @Param("phone") String phone);

  @Query(
      value = "SELECT o.* FROM orders o WHERE o.created_at BETWEEN :startDate AND :endDate",
      nativeQuery = true)
  List<Orders> findByDate(@Param("startDate") Long startDate, @Param("endDate") Long endDate);

  Optional<Orders> findByOrderCode(String code);

  List<Orders> findByUserId(Long id);

  Page<Orders> findAll(Specification<Orders> specification, Pageable pageable);

  @Query(
      value =
          "SELECT o.staff_id , COUNT(o.id) AS orderCount FROM Orders o where o.created_at  between :startDate AND :endDate GROUP BY o.staff_id  ORDER BY orderCount desc limit 1",
      nativeQuery = true)
  List<Object[]> findStaffAndTotalOrderByStaff(
      @Param("startDate") Long startDate, @Param("endDate") Long endDate);
}
