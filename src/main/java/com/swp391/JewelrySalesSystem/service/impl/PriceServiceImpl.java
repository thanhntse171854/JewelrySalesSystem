package com.swp391.JewelrySalesSystem.service.impl;

import com.swp391.JewelrySalesSystem.entity.Gem;
import com.swp391.JewelrySalesSystem.entity.GemPriceList;
import com.swp391.JewelrySalesSystem.entity.MaterialPriceList;
import com.swp391.JewelrySalesSystem.enums.ErrorCode;
import com.swp391.JewelrySalesSystem.exception.PriceListException;
import com.swp391.JewelrySalesSystem.repository.GemPriceRepository;
import com.swp391.JewelrySalesSystem.repository.MaterialPriceRepository;
import com.swp391.JewelrySalesSystem.service.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PriceServiceImpl implements PriceService {
  private final MaterialPriceRepository materialPriceRepository;
  private final GemPriceRepository gemPriceRepository;

  @Override
  public MaterialPriceList findMaterialPriceList(Long materialId) {
    return materialPriceRepository
        .findMaterialPriceList(materialId, System.currentTimeMillis())
        .orElseThrow(() -> new PriceListException(ErrorCode.MATERIAL_PRICE_LIST_NOT_FOUND));
  }

  @Override
  public GemPriceList findGemPriceList(Gem gem) {
    return gemPriceRepository
        .findGemPriceListByGemId(gem.getId(), System.currentTimeMillis())
        .orElseThrow(() -> new PriceListException(ErrorCode.GEM_PRICE_LIST_NOT_FOUND));
  }
}
