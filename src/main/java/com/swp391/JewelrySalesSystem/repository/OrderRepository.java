package com.swp391.JewelrySalesSystem.repository;

import com.swp391.JewelrySalesSystem.entity.Orders;
import java.util.List;
import java.util.Optional;
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

  Optional<Orders> findByOrderCode(String code);

  List<Orders> findByUserId(Long id);
}
