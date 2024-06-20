package com.swp391.JewelrySalesSystem.service.impl;

import com.swp391.JewelrySalesSystem.entity.Gem;
import com.swp391.JewelrySalesSystem.enums.ErrorCode;
import com.swp391.JewelrySalesSystem.exception.GemException;
import com.swp391.JewelrySalesSystem.repository.GemRepository;
import com.swp391.JewelrySalesSystem.service.GemService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GemServiceImpl implements GemService {
  private final GemRepository gemRepository;

  @Override
  public List<Gem> getAllGem() {
    return gemRepository.findAll();
  }

  @Override
  public Gem findGem(Float carat, String color, String Clarity, String cut, String origin) {
    return gemRepository
        .findByCaratAndColorAndClarityAndCutAndOrigin(carat, color, Clarity, cut, origin)
        .orElse(null);
  }

  @Override
  public Gem findById(Long id) {
    return gemRepository.findById(id).orElseThrow(() -> new GemException(ErrorCode.GEM_NOT_FOUND));
  }

  @Override
  public void save(Gem gem) {
    gemRepository.save(gem);
  }
}
