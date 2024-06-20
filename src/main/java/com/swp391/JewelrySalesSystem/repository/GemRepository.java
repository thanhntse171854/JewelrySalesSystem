package com.swp391.JewelrySalesSystem.repository;

import com.swp391.JewelrySalesSystem.entity.Gem;
import com.swp391.JewelrySalesSystem.entity.GemPriceList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GemRepository extends JpaRepository<Gem, Long> {
  List<Gem> findAll(Specification<GemPriceList> spec);

  Optional<Gem> findByCaratAndColorAndClarityAndCutAndOrigin(
      Float carat, String color, String Clarity, String cut, String origin);
}
