package com.swp391.JewelrySalesSystem.service.impl;

import com.swp391.JewelrySalesSystem.entity.Gem;
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
}
