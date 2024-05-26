package com.swp391.JewelrySalesSystem.facade.impl;

import com.swp391.JewelrySalesSystem.entity.Gem;
import com.swp391.JewelrySalesSystem.entity.GemPriceList;
import com.swp391.JewelrySalesSystem.entity.Material;
import com.swp391.JewelrySalesSystem.entity.MaterialPriceList;
import com.swp391.JewelrySalesSystem.facade.PriceFacade;
import com.swp391.JewelrySalesSystem.response.BaseResponse;
import com.swp391.JewelrySalesSystem.response.GemPriceResponse;
import com.swp391.JewelrySalesSystem.response.MaterialPriceResponse;
import com.swp391.JewelrySalesSystem.service.GemService;
import com.swp391.JewelrySalesSystem.service.MaterialService;
import com.swp391.JewelrySalesSystem.service.PriceService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PriceFacadeImpl implements PriceFacade {
  private final PriceService priceService;
  private final MaterialService materialService;
  private final GemService gemService;

  @Override
  public BaseResponse<List<MaterialPriceResponse>> getMaterialPrice() {
    List<MaterialPriceResponse> list = new ArrayList<>();
    List<Material> materials = materialService.getAllMaterial();
    for (Material material : materials) {
      MaterialPriceList materialPriceList = priceService.findMaterialPriceList(material.getId());
      list.add(
          MaterialPriceResponse.builder()
              .materialId(material.getId())
              .materialName(material.getName())
              .materialBuyPrice(materialPriceList.getBuyPrice())
              .materialSellPrice(materialPriceList.getSellPrice())
              .effectDate(materialPriceList.getEffectDate())
              .build());
    }
    return BaseResponse.build(list, true);
  }

  @Override
  public BaseResponse<List<GemPriceResponse>> getGemPrice() {
    List<GemPriceResponse> list = new ArrayList<>();
    List<Gem> gems = gemService.getAllGem();
    for (Gem gem : gems) {
      GemPriceList gemPriceList = priceService.findGemPriceList(gem);
      list.add(
          GemPriceResponse.builder()
              .gemId(gem.getId())
              .origin(gemPriceList.getOrigin())
              .color(gemPriceList.getColor())
              .cut(gemPriceList.getCut())
              .clarity(gemPriceList.getClarity())
              .carat(gemPriceList.getCarat())
              .gemBuyPrice(gemPriceList.getBuyPrice())
              .gemSellPrice(gemPriceList.getSellPrice())
              .effectDate(gemPriceList.getEffectDate())
              .build());
    }
    return BaseResponse.build(list, true);
  }
}
