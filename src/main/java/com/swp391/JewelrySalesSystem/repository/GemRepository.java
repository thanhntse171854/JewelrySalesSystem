package com.swp391.JewelrySalesSystem.repository;

import com.swp391.JewelrySalesSystem.entity.Gem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GemRepository extends JpaRepository<Gem, Long> {}
