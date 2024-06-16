package com.swp391.JewelrySalesSystem.service.impl;

import com.swp391.JewelrySalesSystem.service.DataMapperService;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

@Service
public class DataMapperServiceImpl implements DataMapperService {
  @Override
  public Context setData(Object orderDetailDTO) {
    Context context = new Context();
    context.setVariable("order", orderDetailDTO);
    return context;
  }
}
