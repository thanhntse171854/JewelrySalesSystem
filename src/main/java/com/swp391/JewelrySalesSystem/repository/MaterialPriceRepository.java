package com.swp391.JewelrySalesSystem.repository;

import com.swp391.JewelrySalesSystem.entity.MaterialPriceList;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialPriceRepository extends JpaRepository<MaterialPriceList, Long> {
  @Query(
      value =
          "SELECT mpl.* "
              + "FROM material_price_list mpl "
              + "WHERE mpl.effect_date > :currentTimestamp "
              + "AND mpl.material_id = :materialId "
              + "ORDER BY mpl.effect_date ASC LIMIT 1",
      nativeQuery = true)
  Optional<MaterialPriceList> findMaterialPriceList(
      @Param("materialId") Long id, @Param("currentTimestamp") long currentTimestamp);
}
