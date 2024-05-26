package com.swp391.JewelrySalesSystem.service.impl;

import com.swp391.JewelrySalesSystem.entity.Material;
import com.swp391.JewelrySalesSystem.repository.MaterialRepository;
import com.swp391.JewelrySalesSystem.service.MaterialService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MaterialServiceImpl implements MaterialService {
  private final MaterialRepository materialRepository;

  @Override
  public List<Material> getAllMaterial() {
    return materialRepository.findAll();
  }
}
