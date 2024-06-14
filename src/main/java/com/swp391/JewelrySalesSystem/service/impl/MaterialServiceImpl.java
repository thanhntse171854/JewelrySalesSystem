package com.swp391.JewelrySalesSystem.service.impl;

import com.swp391.JewelrySalesSystem.entity.Material;
import com.swp391.JewelrySalesSystem.enums.ErrorCode;
import com.swp391.JewelrySalesSystem.exception.MaterialException;
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

  @Override
  public Material findById(Long id) {
    return materialRepository
        .findById(id)
        .orElseThrow(() -> new MaterialException(ErrorCode.MATERIAL_NOT_FOUND));
  }
}
