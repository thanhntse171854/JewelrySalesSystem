package com.swp391.JewelrySalesSystem.repository;

import com.swp391.JewelrySalesSystem.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {}
