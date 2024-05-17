package com.swp391.JewelrySalesSystem.service;

import com.swp391.JewelrySalesSystem.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
  User findByPhone(String phone);
}
