package com.swp391.JewelrySalesSystem.facade;

import com.swp391.JewelrySalesSystem.request.UpsertPriceRequest;
import com.swp391.JewelrySalesSystem.response.BaseResponse;
import com.swp391.JewelrySalesSystem.response.GemPriceResponse;
import com.swp391.JewelrySalesSystem.response.MaterialPriceResponse;
import java.util.List;

public interface PriceFacade {
  BaseResponse<List<MaterialPriceResponse>> getMaterialPrice();

  BaseResponse<List<MaterialPriceResponse>> getMaterialNotEffectDate(Long id);

  BaseResponse<List<GemPriceResponse>> getGemNotEffectDate(Long id);

  BaseResponse<List<GemPriceResponse>> getGemPrice();

  BaseResponse<Void> updatePrice(Long id, UpsertPriceRequest request);

  BaseResponse<Void> updatePriceGem(Long id, UpsertPriceRequest request);

  BaseResponse<Void> createPriceMaterial(Long id, UpsertPriceRequest request);
}
