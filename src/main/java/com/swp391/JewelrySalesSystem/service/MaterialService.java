package com.swp391.JewelrySalesSystem.service;

import com.swp391.JewelrySalesSystem.entity.Material;
import java.util.List;
import java.util.Optional;

public interface MaterialService {
  List<Material> getAllMaterial();

  Optional<Material> findById(Long id);
}
