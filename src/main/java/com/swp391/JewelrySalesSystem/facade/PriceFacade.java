package com.swp391.JewelrySalesSystem.facade;

import com.swp391.JewelrySalesSystem.response.BaseResponse;
import com.swp391.JewelrySalesSystem.response.GemPriceResponse;
import com.swp391.JewelrySalesSystem.response.MaterialPriceResponse;
import java.util.List;

public interface PriceFacade {
  BaseResponse<List<MaterialPriceResponse>> getMaterialPrice();

  BaseResponse<List<GemPriceResponse>> getGemPrice();
}
