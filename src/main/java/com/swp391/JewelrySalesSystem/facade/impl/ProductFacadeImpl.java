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
import java.util.List;
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

  @Override
  public BaseResponse<ProductDetailResponse> findById(Long id) {
    Product product = productService.findByProductIdAndActive(id);
    ProductDetailResponse productDetailResponse = buildProductDetailResponse(product);
    return BaseResponse.build(productDetailResponse, true);
  }

  @Override
  public BaseResponse<List<CategoryResponse>> getCategoriesByType(CategoryType categoryType) {
    List<CategoryResponse> list =
        productCategoryService.getCategoryByType(categoryType).stream()
            .map(this::buildCategoryResponse)
            .toList();
    return BaseResponse.build(list, true);
  }

  private ProductResponse buildProductResponse(Product product) {
    float totalPrice = calculateTotalPrice(product);
    return ProductResponse.builder()
        .productId(product.getId())
        .productCode(product.getProductCode())
        .productName(product.getProductName())
        .productImage("https://trangsuc.doji.vn/Upload/afrj000201f3cz1_1.jpg")
        .productPrice(totalPrice)
        .build();
  }

  private ProductDetailResponse buildProductDetailResponse(Product product) {
    float totalPrice = calculateTotalPrice(product);
    List<MaterialDTO> materialDTOS = buildMaterialDTOs(product);
    List<SizeDTO> sizeDTOS = buildSizeDTOs(product);
    List<GemDTO> gemDTOS = getInformationGem(product);

    return ProductDetailResponse.builder()
        .id(product.getId())
        .productCode(product.getProductCode())
        .productName(product.getProductName())
        .gender(product.getGender())
        .gemCost(product.getGemCost())
        .productionCost(product.getProductionCost())
        .category(product.getCategory().getCategoryName())
        .sizeProducts(sizeDTOS)
        .materials(materialDTOS)
        .totalPrice(totalPrice)
        .gem(gemDTOS)
        .build();
  }

  private CategoryResponse buildCategoryResponse(ProductCategory category) {
    return CategoryResponse.builder()
        .id(category.getId())
        .categoryName(category.getCategoryName())
        .categoryType(category.getCategoryType().toString())
        .build();
  }

  private float calculateTotalPrice(Product product) {
    float materialPrice = calculateMaterialPrice(product);
    float gemPrice =
        product.getIsGem()
            ? (float) getInformationGem(product).stream().mapToDouble(GemDTO::getTotalPrice).sum()
            : 0;
    float totalPrice =
        materialPrice + gemPrice + (product.getGemCost() == null ? 0 : product.getGemCost());

    boolean isGoldProduct = product.getCategory().getCategoryType().equals(CategoryType.GOLD);
    if (isGoldProduct) {
      totalPrice += product.getProductionCost();
    }
    return totalPrice;
  }

  private float calculateMaterialPrice(Product product) {
    boolean isDiamondProduct = product.getCategory().getCategoryType().equals(CategoryType.DIAMOND);
    if (isDiamondProduct) return 0;

    return product.getProductMaterials().stream()
        .map(
            productM ->
                getProductMaterialWeight(product, productM.getMaterial())
                    * getMaxPriceForMaterial(productM.getMaterial()))
        .reduce(0f, Float::sum);
  }

  private float getProductMaterialWeight(@NotNull Product product, Material material) {
    for (ProductMaterial productMaterial : product.getProductMaterials()) {
      if (productMaterial.getMaterial().equals(material)) {
        return productMaterial.getWeight();
      }
    }
    return 0;
  }

  private List<MaterialDTO> buildMaterialDTOs(Product product) {
    boolean isDiamondProduct = product.getCategory().getCategoryType().equals(CategoryType.DIAMOND);
    if (isDiamondProduct) {
      return new ArrayList<>();
    }

    return product.getProductMaterials().stream()
        .map(
            productM ->
                new MaterialDTO(
                    productM.getMaterial().getId(),
                    productM.getMaterial().getName(),
                    getProductMaterialWeight(product, productM.getMaterial())))
        .toList();
  }

  private List<SizeDTO> buildSizeDTOs(Product product) {
    boolean isDiamondProduct = product.getCategory().getCategoryType().equals(CategoryType.DIAMOND);
    if (isDiamondProduct) return new ArrayList<>();

    return product.getSizeProducts().stream()
        .map(
            sizeProduct ->
                new SizeDTO(
                    sizeProduct.getSize().getId(),
                    sizeProduct.getSize().getSize(),
                    sizeProduct.getQuantity()))
        .toList();
  }

  private Long getMaxPriceSellForGem(@NotNull Gem gem) {
    return priceService.findGemPriceList(gem).getSellPrice();
  }

  private long getMaxPriceForMaterial(@NotNull Material material) {
    return priceService.findMaterialPriceList(material.getId()).getSellPrice();
  }

  private List<GemDTO> getInformationGem(Product product) {
    return product.getProductGems().stream()
        .map(
            productGem -> {
              Long price = getMaxPriceSellForGem(productGem.getGem());
              Long quantity =
                  product.getCategory().getCategoryType().equals(CategoryType.DIAMOND)
                      ? productGem.getGem().getQuantity()
                      : 0L;

              return GemDTO.builder()
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
                  .build();
            })
        .toList();
  }
}
