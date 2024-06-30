package com.swp391.JewelrySalesSystem.facade.impl;

import com.swp391.JewelrySalesSystem.entity.Material;
import com.swp391.JewelrySalesSystem.enums.ErrorCode;
import com.swp391.JewelrySalesSystem.exception.MaterialException;
import com.swp391.JewelrySalesSystem.facade.MaterialFacade;
import com.swp391.JewelrySalesSystem.request.UpsertMaterialRequest;
import com.swp391.JewelrySalesSystem.response.BaseResponse;
import com.swp391.JewelrySalesSystem.response.MaterialResponse;
import com.swp391.JewelrySalesSystem.service.MaterialService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MaterialFacadeImpl implements MaterialFacade {

  private final MaterialService materialService;

  @Override
  public BaseResponse<Void> updateMaterial(Long id, UpsertMaterialRequest request) {
    Material material = materialService.findById(id);
    if (request.getName() == null) throw new MaterialException(ErrorCode.CREATE_MATERIAL_NULL);

    material.updateName(request.getName());
    materialService.save(material);
    return BaseResponse.ok();
  }

  @Override
  public BaseResponse<List<MaterialResponse>> getAllMaterial() {
    List<Material> materials = materialService.getAllMaterial();
    List<MaterialResponse> list =
        materials.stream()
            .map(
                material -> {
                  return MaterialResponse.builder()
                      .material(material.getName())
                      .id(material.getId())
                      .build();
                })
            .collect(Collectors.toList());
    return BaseResponse.build(list, true);
  }

  @Override
  public BaseResponse<Void> createMaterial(UpsertMaterialRequest request) {
    if (request.getName() == null) throw new MaterialException(ErrorCode.CREATE_MATERIAL_NULL);

    materialService.save(Material.builder().name(request.getName()).build());
    return BaseResponse.ok();
  }

  @Override
  public BaseResponse<Void> deleteMaterial(Long id) {
    Material material = materialService.findById(id);
    materialService.delete(material);
    return BaseResponse.ok();
  }
}
