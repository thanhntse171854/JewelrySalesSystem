package com.swp391.JewelrySalesSystem.service.impl;

import com.swp391.JewelrySalesSystem.request.PreOrderRequest;
import com.swp391.JewelrySalesSystem.service.CacheService;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CacheServiceImpl implements CacheService {

  private final RedisTemplate<String, Object> redisTemplate;

  @Override
  public void store(String key, PreOrderRequest value, Integer timeout, TimeUnit timeUnit) {
    this.redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
  }

  @Override
  public Object retrieve(String key) {
    return redisTemplate.opsForValue().get(key);
  }

  @Override
  public void delete(String key) {
    this.redisTemplate.delete(key);
  }

  @Override
  public Boolean hasKey(String key) {
    return this.redisTemplate.hasKey(key);
  }

  @Override
  public List<String> getAllKeys() {
    Set<String> keys = redisTemplate.keys("*");
    if (keys != null) {
      return new ArrayList<>(keys);
    } else {
      return new ArrayList<>();
    }
  }
}
