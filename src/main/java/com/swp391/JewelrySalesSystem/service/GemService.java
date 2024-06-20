package com.swp391.JewelrySalesSystem.service;

import com.swp391.JewelrySalesSystem.entity.Gem;
import java.util.List;

public interface GemService {
  List<Gem> getAllGem();

  Gem findGem(Float carat, String color, String Clarity, String cut, String origin);

  Gem findById(Long code);

  void save(Gem gem);
}
