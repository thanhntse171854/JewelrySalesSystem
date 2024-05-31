package com.swp391.JewelrySalesSystem.service;

import com.swp391.JewelrySalesSystem.request.PreOrderRequest;

import java.util.List;
import java.util.concurrent.TimeUnit;

public interface CacheService {
  void store(String key, PreOrderRequest value, Integer timeout, TimeUnit timeUnit);

  Object retrieve(String key);

  void delete(String key);

  Boolean hasKey(String key);

  List<String> getAllKeys();
}
