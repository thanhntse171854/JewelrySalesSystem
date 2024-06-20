package com.swp391.JewelrySalesSystem.specifications;

import com.swp391.JewelrySalesSystem.entity.Orders;
import com.swp391.JewelrySalesSystem.entity.User;
import com.swp391.JewelrySalesSystem.enums.DeliveryStatus;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class OrderSpecifications {
  public static Specification<Orders> baseSpecification(Long staff) {
    return (root, query, criteriaBuilder) -> {
      if (staff == null) {
        return null;
      }

      Join<Orders, User> userJoin = root.join("user", JoinType.INNER);
      return criteriaBuilder.equal(userJoin.get("id"), staff);
    };
  }

  public static Specification<Orders> filterByOrderCode(String orderCode) {
    return (root, query, criteriaBuilder) -> {
      if (orderCode == null || orderCode.trim().isEmpty()) {
        return null;
      }
      return criteriaBuilder.like(root.get("orderCode"), "%" + orderCode + "%");
    };
  }

  public static Specification<Orders> filterByDeliveryStatus(DeliveryStatus deliveryStatus) {
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.equal(root.get("deliveryStatus"), deliveryStatus);
  }
}
