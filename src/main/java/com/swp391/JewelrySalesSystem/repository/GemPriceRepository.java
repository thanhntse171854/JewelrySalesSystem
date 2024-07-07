package com.swp391.JewelrySalesSystem.repository;

import com.swp391.JewelrySalesSystem.entity.GemPriceList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GemPriceRepository extends JpaRepository<GemPriceList, Long> {

  @Query(
      value =
          "SELECT gpl.* "
              + "FROM gem_price_list gpl "
              + "INNER JOIN gems g ON gpl.origin = g.origin "
              + "AND gpl.color = g.color "
              + "AND gpl.clarity = g.clarity "
              + "AND gpl.cut = g.cut "
              + "AND gpl.carat = g.carat "
              + "WHERE gpl.effect_date <  :currentTimestamp "
              + "AND g.id = :gemId "
              + "ORDER BY gpl.effect_date DESC LIMIT 1",
      nativeQuery = true)
  Optional<GemPriceList> findGemPriceListByGemId(
      @Param("gemId") Long gemId, @Param("currentTimestamp") long currentTimestamp);

  List<GemPriceList> findAll(Specification<GemPriceList> spec);

  @Query(
      value =
          "SELECT gpl.* "
              + "FROM gem_price_list gpl "
              + "INNER JOIN gems g ON gpl.origin = g.origin "
              + "AND gpl.color = g.color "
              + "AND gpl.clarity = g.clarity "
              + "AND gpl.cut = g.cut "
              + "AND gpl.carat = g.carat "
              + "WHERE  g.id = :gemId ",
      nativeQuery = true)
  List<GemPriceList> findGemPriceListByGemIdNotEffectDate(@Param("gemId") Long gemId);
}
