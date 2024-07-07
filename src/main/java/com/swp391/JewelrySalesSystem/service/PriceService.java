package com.swp391.JewelrySalesSystem.service;

import com.swp391.JewelrySalesSystem.entity.Gem;
import com.swp391.JewelrySalesSystem.entity.GemPriceList;
import com.swp391.JewelrySalesSystem.entity.MaterialPriceList;
import com.swp391.JewelrySalesSystem.request.GemFilterRequest;
import java.util.List;

public interface PriceService {
  MaterialPriceList findMaterialPriceList(Long materialId);

  MaterialPriceList findMaterialPriceListById(Long id);

  List<MaterialPriceList> findMaterialNotEffectDate(Long materialId);

  List<GemPriceList> findGemNotEffectDate(Long gem);

  GemPriceList findGemPriceList(Gem gem);

  GemPriceList findGemPriceListById(Long id);

  List<Gem> filterGemPriceLists(GemFilterRequest request);

  void saveMaterial(MaterialPriceList materialPriceList);

  void saveGem(GemPriceList gemPriceList);
}
