package com.swp391.JewelrySalesSystem.service.impl;

import com.swp391.JewelrySalesSystem.entity.Gem;
import com.swp391.JewelrySalesSystem.entity.GemPriceList;
import com.swp391.JewelrySalesSystem.entity.MaterialPriceList;
import com.swp391.JewelrySalesSystem.enums.ErrorCode;
import com.swp391.JewelrySalesSystem.exception.PriceListException;
import com.swp391.JewelrySalesSystem.repository.GemPriceRepository;
import com.swp391.JewelrySalesSystem.repository.GemRepository;
import com.swp391.JewelrySalesSystem.repository.MaterialPriceRepository;
import com.swp391.JewelrySalesSystem.request.GemFilterRequest;
import com.swp391.JewelrySalesSystem.service.PriceService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PriceServiceImpl implements PriceService {
  private final MaterialPriceRepository materialPriceRepository;
  private final GemPriceRepository gemPriceRepository;
  private final GemRepository gemRepository;

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
        .orElse(null);
  }

  @Override
  public List<Gem> filterGemPriceLists(GemFilterRequest request) {
    Specification<GemPriceList> spec = Specification.where(null);

    if (request.getCut() != null && !request.getCut().isEmpty()) {
      spec =
          spec.and(
              (root, query, criteriaBuilder) ->
                  criteriaBuilder.equal(root.get("cut"), request.getCut()));
    }
    if (request.getCarat() != null) {
      spec =
          spec.and(
              (root, query, criteriaBuilder) ->
                  criteriaBuilder.equal(root.get("carat"), request.getCarat()));
    }
    if (request.getClarity() != null && !request.getClarity().isEmpty()) {
      spec =
          spec.and(
              (root, query, criteriaBuilder) ->
                  criteriaBuilder.equal(root.get("clarity"), request.getClarity()));
    }
    if (request.getColor() != null && !request.getColor().isEmpty()) {
      spec =
          spec.and(
              (root, query, criteriaBuilder) ->
                  criteriaBuilder.equal(root.get("color"), request.getColor()));
    }
    if (request.getOrigin() != null && !request.getOrigin().isEmpty()) {
      spec =
          spec.and(
              (root, query, criteriaBuilder) ->
                  criteriaBuilder.equal(root.get("origin"), request.getOrigin()));
    }
    return gemRepository.findAll(spec);
  }

  @Override
  public MaterialPriceList findMaterialPriceListById(Long id) {
    return materialPriceRepository
        .findById(id)
        .orElseThrow(() -> new PriceListException(ErrorCode.MATERIAL_PRICE_LIST));
  }

  @Override
  public List<MaterialPriceList> findMaterialNotEffectDate(Long materialId) {
    return materialPriceRepository.findMaterialPriceListNotEffectDate(materialId);
  }

  @Override
  public List<GemPriceList> findGemNotEffectDate(Long gem) {
    return gemPriceRepository.findGemPriceListByGemIdNotEffectDate(gem);
  }

  @Override
  public GemPriceList findGemPriceListById(Long id) {
    return gemPriceRepository
        .findById(id)
        .orElseThrow(() -> new PriceListException(ErrorCode.MATERIAL_PRICE_LIST));
  }

  @Override
  public void saveMaterial(MaterialPriceList materialPriceList) {
    materialPriceRepository.save(materialPriceList);
  }

  @Override
  public void saveGem(GemPriceList gemPriceList) {
    gemPriceRepository.save(gemPriceList);
  }
}
