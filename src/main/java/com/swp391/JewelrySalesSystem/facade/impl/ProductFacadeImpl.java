package com.swp391.JewelrySalesSystem.facade.impl;

import com.swp391.JewelrySalesSystem.dto.GemDTO;
import com.swp391.JewelrySalesSystem.dto.MaterialDTO;
import com.swp391.JewelrySalesSystem.entity.*;
import com.swp391.JewelrySalesSystem.enums.CategoryType;
import com.swp391.JewelrySalesSystem.enums.ErrorCode;
import com.swp391.JewelrySalesSystem.exception.ProductException;
import com.swp391.JewelrySalesSystem.facade.ProductFacade;
import com.swp391.JewelrySalesSystem.request.CreateProductRequest;
import com.swp391.JewelrySalesSystem.request.ProductCriteria;
import com.swp391.JewelrySalesSystem.response.*;
import com.swp391.JewelrySalesSystem.service.*;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ProductFacadeImpl implements ProductFacade {
  private final ProductService productService;
  private final PriceService priceService;
  private final ProductCategoryService productCategoryService;
  private final MaterialService materialService;
  private final CloudinaryService cloudinaryService;

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
    String productImage =
        "https://upload.wikimedia.org/wikipedia/commons/thumb/6/65/No-Image-Placeholder.svg/832px-No-Image-Placeholder.svg.png";

    if (product.getProductAssets() != null && !product.getProductAssets().isEmpty()) {
      String mediaUrl = product.getProductAssets().get(0).getMediaUrl();
      if (mediaUrl != null && !mediaUrl.isEmpty()) {
        productImage = mediaUrl;
      }
    }
    return ProductResponse.builder()
        .productId(product.getId())
        .productCode(product.getProductCode())
        .productName(product.getProductName())
        .productImage(productImage)
        .productPrice(totalPrice)
        .size(product.getSize())
        .build();
  }

  private ProductDetailResponse buildProductDetailResponse(Product product) {
    float totalPrice = calculateTotalPrice(product);
    List<MaterialDTO> materialDTOS = buildMaterialDTOs(product);
    List<GemDTO> gemDTOS = getInformationGem(product);
    ProductAssetResponse productAsset = buildProductAssetResponse(product);

    return ProductDetailResponse.builder()
        .id(product.getId())
        .productCode(product.getProductCode())
        .productName(product.getProductName())
        .gender(product.getGender())
        .gemCost(product.getGemCost())
        .productionCost(product.getProductionCost())
        .category(product.getCategory().getCategoryName())
        .materials(materialDTOS)
        .productAsset(productAsset)
        .totalPrice(totalPrice)
        .gem(gemDTOS)
        .size(product.getSize())
        .build();
  }

  private ProductAssetResponse buildProductAssetResponse(Product product) {
    List<ProductAsset> productAssets = product.getProductAssets();

    String img1 = null;
    String img2 = null;
    String img3 = null;
    String img4 = null;

    if (productAssets != null && !productAssets.isEmpty()) {
      if (productAssets.size() > 0) img1 = productAssets.get(0).getMediaUrl();
      if (productAssets.size() > 1) img2 = productAssets.get(1).getMediaUrl();
      if (productAssets.size() > 2) img3 = productAssets.get(2).getMediaUrl();
      if (productAssets.size() > 3) img4 = productAssets.get(3).getMediaUrl();
    }

    if (img1 == null && img2 == null && img3 == null && img4 == null) {
      return null;
    }

    return ProductAssetResponse.builder().img1(img1).img2(img2).img3(img3).img4(img4).build();
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

  private Float getMaxPriceSellForGem(@NotNull Gem gem) {
    return priceService.findGemPriceList(gem).getSellPrice();
  }

  private Float getMaxPriceForMaterial(@NotNull Material material) {
    return priceService.findMaterialPriceList(material.getId()).getSellPrice();
  }

  private List<GemDTO> getInformationGem(Product product) {
    return product.getProductGems().stream()
        .map(
            productGem -> {
              Float price = getMaxPriceSellForGem(productGem.getGem());

              return GemDTO.builder()
                  .id(productGem.getGem().getId())
                  .gemName(productGem.getGem().getGemName())
                  .gemCode(productGem.getGem().getGemCode())
                  .color(productGem.getGem().getColor())
                  .clarity(productGem.getGem().getClarity())
                  .cut(productGem.getGem().getCut())
                  .origin(productGem.getGem().getOrigin())
                  .priceSell(price)
                  .carat(productGem.getGem().getCarat())
                  .totalPrice(price + product.getProductionCost())
                  .build();
            })
        .toList();
  }

  @SneakyThrows
  @Override
  public BaseResponse<Void> createProduct(
      CreateProductRequest request, List<MultipartFile> images) {
    if (productService.findByProductCode(request.getProductCode()) != null)
      throw new ProductException(ErrorCode.PRODUCT_IS_EXIST);

    ProductCategory productCategory = productCategoryService.findById(request.getCategoryId());

    Product product =
        Product.builder()
            .productCode(request.getProductCode())
            .productName(request.getProductName())
            .gemCost(request.getGemCost())
            .productionCost(request.getProductionCost())
            .gender(request.getGender())
            .category(productCategory)
            .isGem(true)
            .size(request.getSize())
            .build();

    if (request.getMaterialProductRequests() != null) {
      for (var materialP : request.getMaterialProductRequests()) {
        Material material = materialService.findById(materialP.getMaterial());
        ProductMaterial productMaterial =
            ProductMaterial.builder()
                .product(product)
                .material(material)
                .weight(materialP.getWeight())
                .build();

        product.addProductMaterial(productMaterial);
      }
    }

    for (MultipartFile file : images) {
      String url = cloudinaryService.uploadImage(file.getBytes());

      ProductAsset productAsset =
          ProductAsset.builder()
              .mediaKey(file.getOriginalFilename())
              .mediaUrl(url)
              .product(product)
              .build();

      product.getProductAssets().add(productAsset);
    }

    productService.save(product);
    return BaseResponse.ok();
  }

  @Override
  public BaseResponse<Void> deleteProduct(String code) {
    Product product = productService.findByProductCode(code);
    if (product == null) {
      throw new ProductException(ErrorCode.PRODUCT_NOT_FOUND_OR_DELETED);
    }
    productService.deactivateProduct(product.getId());
    return BaseResponse.ok();
  }
}
