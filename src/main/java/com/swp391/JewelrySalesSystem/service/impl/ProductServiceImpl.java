package com.swp391.JewelrySalesSystem.service.impl;

import com.swp391.JewelrySalesSystem.entity.Product;
import com.swp391.JewelrySalesSystem.enums.ErrorCode;
import com.swp391.JewelrySalesSystem.exception.EntityNotFoundException;
import com.swp391.JewelrySalesSystem.repository.ProductRepository;
import com.swp391.JewelrySalesSystem.request.ProductCriteria;
import com.swp391.JewelrySalesSystem.service.ProductService;
import com.swp391.JewelrySalesSystem.specifications.ProductSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
  private final ProductRepository productRepository;

  @Override
  public Product findByProductIdAndActive(Long id) {
    return productRepository
        .findByIdAndIsActive(id, true)
        .orElseThrow(() -> new EntityNotFoundException(ErrorCode.PRODUCT_NOT_FOUND_OR_DELETED));
  }

  @Override
  public Page<Product> findByFilter(ProductCriteria criteria) {
    int currentPage = (criteria.getCurrentPage() == null) ? 1 : criteria.getCurrentPage();
    int pageSize = (criteria.getPageSize() == null) ? 10 : criteria.getPageSize();
    Pageable pageable = PageRequest.of(Math.max(currentPage - 1, 0), pageSize);
    Specification<Product> specification = ProductSpecifications.baseSpecification();
    if (criteria.getCategoryId() != null) {
      specification =
          specification.and(ProductSpecifications.filterByCategory(criteria.getCategoryId()));
    }

    if (criteria.getGender() != null) {
      specification = specification.and(ProductSpecifications.filterByGender(criteria.getGender()));
    }

    if (criteria.getCategoryType() != null) {
      specification =
          specification.and(ProductSpecifications.filterByCategoryType(criteria.getCategoryType()));
    }
    if (criteria.getProductCode() != null) {
      specification =
          specification.and(ProductSpecifications.filterByProductCode(criteria.getProductCode()));
    }

    return productRepository.findAll(specification, pageable);
  }

  @Override
  public Product findByProductCode(String code) {
    return productRepository.findByProductCode(code);
  }

  @Override
  public void deactivateProduct(Long productId) {
    productRepository.deactivateProductById(productId);
  }
}
