package com.swp391.JewelrySalesSystem.facade.impl;

import com.swp391.JewelrySalesSystem.dto.GemDTO;
import com.swp391.JewelrySalesSystem.dto.MaterialDTO;
import com.swp391.JewelrySalesSystem.dto.SizeDTO;
import com.swp391.JewelrySalesSystem.entity.*;
import com.swp391.JewelrySalesSystem.enums.CategoryType;
import com.swp391.JewelrySalesSystem.facade.ProductFacade;
import com.swp391.JewelrySalesSystem.request.ProductCriteria;
import com.swp391.JewelrySalesSystem.response.*;
import com.swp391.JewelrySalesSystem.service.PriceService;
import com.swp391.JewelrySalesSystem.service.ProductCategoryService;
import com.swp391.JewelrySalesSystem.service.ProductService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductFacadeImpl implements ProductFacade {
  private final ProductService productService;
  private final PriceService priceService;
  private final ProductCategoryService productCategoryService;

  @Override
  public BaseResponse<PaginationResponse<List<ProductResponse>>> findByFilter(
      ProductCriteria criteria) {
    var result = productService.findByFilter(criteria);
    List<ProductResponse> response =
        result.getContent().stream().map(this::buildProductResponse).toList();
    int currentPage = (criteria.getCurrentPage() == null) ? 1 : criteria.getCurrentPage();
    return BaseResponse.build(PaginationResponse.build(response, result, currentPage), true);
  }

  private ProductResponse buildProductResponse(Product product) {
    float diamondPrice = 0;
    float materialPrice = 0;
    float totalPrice = 0;
    List<GemDTO> list = new ArrayList<>();

    boolean isNotDiamondProduct =
        !product.getCategory().getCategoryType().equals(CategoryType.DIAMOND);
    if (isNotDiamondProduct) {
      var productMaterial = product.getProductMaterials();
      if (product.getProductMaterials() != null) {
        for (ProductMaterial productM : productMaterial) {
          float weight = getProductMaterialWeight(product, productM.getMaterial());
          long maxPrice = getMaxPriceForMaterial(productM.getMaterial());
          materialPrice += weight * maxPrice;
        }
      }
    }

    if (product.getIsGem()) list = getInformationGem(product);
    boolean isGoldProduct = product.getCategory().getCategoryType().equals(CategoryType.GOLD);
    if (isGoldProduct) {
      totalPrice =
          materialPrice
              + product.getProductionCost()
              + (product.getGemCost() == null ? 0 : product.getGemCost());
    } else {
      float gemPrice = (float) list.stream().mapToDouble(GemDTO::getTotalPrice).sum();
      totalPrice =
          materialPrice + gemPrice + (product.getGemCost() == null ? 0 : product.getGemCost());
    }

    return ProductResponse.builder()
        .productId(product.getId())
        .productCode(product.getProductCode())
        .productName(product.getProductName())
        .productImage("https://trangsuc.doji.vn/Upload/afrj000201f3cz1_1.jpg")
        .productPrice(totalPrice)
        .build();
  }

  @Override
  public BaseResponse<ProductDetailResponse> findById(Long id) {
    Product product = productService.findByProductIdAndActive(id);

    float materialPrice = 0;
    float totalPrice;
    List<MaterialDTO> materialDTOS = new ArrayList<>();
    List<SizeDTO> size = new ArrayList<>();
    List<GemDTO> list = new ArrayList<>();

    boolean isNotDiamondProduct =
        !product.getCategory().getCategoryType().equals(CategoryType.DIAMOND);
    if (isNotDiamondProduct) {
      var productSize = product.getSizeProducts();
      var productMaterial = product.getProductMaterials();
      if (product.getProductMaterials() != null) {
        for (ProductMaterial productM : productMaterial) {
          float weight = getProductMaterialWeight(product, productM.getMaterial());
          long maxPrice = getMaxPriceForMaterial(productM.getMaterial());
          materialPrice += weight * maxPrice;
          materialDTOS.add(new MaterialDTO(productM.getMaterial().getId(),productM.getMaterial().getName(), weight));
        }
      }

      for (SizeProduct sizeProduct : productSize) {
        size.add(new SizeDTO(sizeProduct.getSize().getId(), sizeProduct.getSize().getSize(), sizeProduct.getQuantity()));
      }
    }

    if (product.getIsGem()) list = getInformationGem(product);
    boolean isGoldProduct = product.getCategory().getCategoryType().equals(CategoryType.GOLD);
    if (isGoldProduct) {
      totalPrice =
          materialPrice
              + product.getProductionCost()
              + (product.getGemCost() == null ? 0 : product.getGemCost());
    } else {
      float gemPrice = (float) list.stream().mapToDouble(GemDTO::getTotalPrice).sum();
      totalPrice =
          materialPrice + gemPrice + (product.getGemCost() == null ? 0 : product.getGemCost());
    }
    return BaseResponse.build(
        ProductDetailResponse.builder()
            .id(product.getId())
            .productCode(product.getProductCode())
            .productName(product.getProductName())
            .gender(product.getGender())
            .gemCost(product.getGemCost())
            .productionCost(product.getProductionCost())
            .category(product.getCategory().getCategoryName())
            .sizeProducts(size)
            .materials(materialDTOS)
            .totalPrice(totalPrice)
            .gem(list)
            .build(),
        true);
  }

  @Override
  public BaseResponse<List<CategoryResponse>> getCategoriesByType(CategoryType categoryType) {
    List<CategoryResponse> list = new ArrayList<>();
    List<ProductCategory> categoryList = productCategoryService.getCategoryByType(categoryType);
    for (ProductCategory category : categoryList) {
      list.add(
          CategoryResponse.builder()
              .id(category.getId())
              .categoryName(category.getCategoryName())
              .categoryType(category.getCategoryType().toString())
              .build());
    }
    return BaseResponse.build(list, true);
  }

  private Long getMaxPriceSellForGem(@NotNull Gem gem) {
    return priceService.findGemPriceList(gem).getSellPrice();
  }

  private float getProductMaterialWeight(@NotNull Product product, Material material) {
    for (ProductMaterial productMaterial : product.getProductMaterials()) {
      if (productMaterial.getMaterial().equals(material)) {
        return productMaterial.getWeight();
      }
    }
    return 0;
  }

  private long getMaxPriceForMaterial(@NotNull Material material) {
    return priceService.findMaterialPriceList(material.getId()).getSellPrice();
  }

  private List<GemDTO> getInformationGem(Product product) {
    List<GemDTO> list = new ArrayList<>();
    for (ProductGem productGem : product.getProductGems()) {
      Long price = getMaxPriceSellForGem(productGem.getGem());
      Long quantity = 0L;

      if (product.getCategory().getCategoryType().equals(CategoryType.DIAMOND)) {
        quantity = productGem.getGem().getQuantity();
      }

      list.add(
          GemDTO.builder()
              .id(productGem.getGem().getId())
              .gemName(productGem.getGem().getGemName())
              .gemCode(productGem.getGem().getGemCode())
              .color(productGem.getGem().getColor())
              .clarity(productGem.getGem().getClarity())
              .cut(productGem.getGem().getCut())
              .origin(productGem.getGem().getOrigin())
              .priceSell(price)
              .weight(productGem.getGem().getCarat())
              .quantity(quantity)
              .totalPrice((float) (price + product.getProductionCost()))
              .build());
    }
    return list;
  }
}
