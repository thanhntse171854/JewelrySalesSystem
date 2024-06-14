package com.swp391.JewelrySalesSystem.facade;

import com.swp391.JewelrySalesSystem.response.BaseResponse;
import com.swp391.JewelrySalesSystem.response.CategoryResponse;
import java.util.List;

public interface CategoryFacade {
  BaseResponse<List<CategoryResponse>> getAllCategory();
}
