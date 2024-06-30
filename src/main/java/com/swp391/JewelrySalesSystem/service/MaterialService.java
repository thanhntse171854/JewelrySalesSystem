package com.swp391.JewelrySalesSystem.service;

import com.swp391.JewelrySalesSystem.entity.Material;
import java.util.List;

public interface MaterialService {
  List<Material> getAllMaterial();

  Material findById(Long id);

  void save(Material material);

  void delete(Material material);
}
