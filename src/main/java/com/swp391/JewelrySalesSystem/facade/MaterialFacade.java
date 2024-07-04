package com.swp391.JewelrySalesSystem.facade;

import com.swp391.JewelrySalesSystem.request.UpsertMaterialRequest;
import com.swp391.JewelrySalesSystem.response.BaseResponse;
import com.swp391.JewelrySalesSystem.response.MaterialResponse;
import java.util.List;

public interface MaterialFacade {
  BaseResponse<List<MaterialResponse>> getAllMaterial();

  BaseResponse<Void> updateMaterial(Long id, UpsertMaterialRequest request);

  BaseResponse<Void> createMaterial(UpsertMaterialRequest request);

  BaseResponse<Void> deleteMaterial(Long id);
}
