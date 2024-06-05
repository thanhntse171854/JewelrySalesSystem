package com.swp391.JewelrySalesSystem.service;

import com.swp391.JewelrySalesSystem.entity.Gem;
import com.swp391.JewelrySalesSystem.entity.GemPriceList;
import com.swp391.JewelrySalesSystem.entity.MaterialPriceList;
import com.swp391.JewelrySalesSystem.request.GemFilterRequest;
import java.util.List;

public interface PriceService {
  MaterialPriceList findMaterialPriceList(Long materialId);

  GemPriceList findGemPriceList(Gem gem);

  List<Gem> filterGemPriceLists(GemFilterRequest request);
}
