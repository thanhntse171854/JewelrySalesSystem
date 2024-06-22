package com.swp391.JewelrySalesSystem.repository;

import com.swp391.JewelrySalesSystem.entity.OrderDetail;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

  @Query(
      value =
          "select pc.category_type, COUNT(od.id) AS totalSold from order_details od join products p on od.product_id = p.id join product_categories pc on p.category_id = pc.id group by pc.category_type order by totalSold DESC\n",
      nativeQuery = true)
  List<Object[]> findCategoryTypeMostOrder();
}
