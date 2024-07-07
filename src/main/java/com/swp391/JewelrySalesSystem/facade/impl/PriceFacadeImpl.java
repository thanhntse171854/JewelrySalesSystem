package com.swp391.JewelrySalesSystem.facade.impl;

import com.swp391.JewelrySalesSystem.entity.Gem;
import com.swp391.JewelrySalesSystem.entity.GemPriceList;
import com.swp391.JewelrySalesSystem.entity.Material;
import com.swp391.JewelrySalesSystem.entity.MaterialPriceList;
import com.swp391.JewelrySalesSystem.enums.ErrorCode;
import com.swp391.JewelrySalesSystem.exception.PriceListException;
import com.swp391.JewelrySalesSystem.facade.PriceFacade;
import com.swp391.JewelrySalesSystem.request.UpsertPriceRequest;
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
              .id(materialPriceList.getId())
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
      if (gemPriceList != null) {
        list.add(
            GemPriceResponse.builder()
                .id(gemPriceList.getId())
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
      } else {
        list.add(
            GemPriceResponse.builder()
                .gemId(gem.getId())
                .origin(gem.getOrigin())
                .color(gem.getColor())
                .cut(gem.getCut())
                .clarity(gem.getClarity())
                .carat(gem.getCarat())
                .build());
      }
    }
    return BaseResponse.build(list, true);
  }

  @Override
  public BaseResponse<List<MaterialPriceResponse>> getMaterialNotEffectDate(Long id) {

    List<MaterialPriceList> materialPriceList = priceService.findMaterialNotEffectDate(id);
    Material material = materialService.findById(id);
    List<MaterialPriceResponse> list = new ArrayList<>();
    for (MaterialPriceList mtpl : materialPriceList) {
      list.add(
          MaterialPriceResponse.builder()
              .id(mtpl.getId())
              .materialId(material.getId())
              .materialName(material.getName())
              .materialBuyPrice(mtpl.getBuyPrice())
              .materialSellPrice(mtpl.getSellPrice())
              .effectDate(mtpl.getEffectDate())
              .build());
    }
    return BaseResponse.build(list, true);
  }

  @Override
  public BaseResponse<List<GemPriceResponse>> getGemNotEffectDate(Long id) {
    List<GemPriceList> gemPriceLists = priceService.findGemNotEffectDate(id);
    List<GemPriceResponse> list = new ArrayList<>();
    Gem gem = gemService.findById(id);
    for (GemPriceList gemPriceList : gemPriceLists) {
      list.add(
          GemPriceResponse.builder()
              .id(gemPriceList.getId())
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

  @Override
  public BaseResponse<Void> updatePrice(Long id, UpsertPriceRequest request) {
    MaterialPriceList materialPriceList = priceService.findMaterialPriceListById(id);

    materialPriceList.updatePrice(request.getPriceSell(), request.getPriceBuy());

    priceService.saveMaterial(materialPriceList);

    return BaseResponse.ok();
  }

  @Override
  public BaseResponse<Void> updatePriceGem(Long id, UpsertPriceRequest request) {
    GemPriceList gemPriceList = priceService.findGemPriceListById(id);
    gemPriceList.updatePrice(request.getPriceSell(), request.getPriceBuy());
    priceService.saveGem(gemPriceList);
    return BaseResponse.ok();
  }

  @Override
  public BaseResponse<Void> createPriceMaterial(Long id, UpsertPriceRequest request) {
    Material material = materialService.findById(id);

    Long effectDate = request.getEffectDate();
    if (effectDate <= System.currentTimeMillis()) {
      throw new PriceListException(ErrorCode.CREATE_PRICE_WRONG);
    }
    priceService.saveMaterial(
        MaterialPriceList.builder()
            .material(material)
            .buyPrice(request.getPriceBuy())
            .sellPrice(request.getPriceSell())
            .effectDate(request.getEffectDate())
            .build());
    return BaseResponse.ok();
  }
}
