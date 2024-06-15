package com.swp391.JewelrySalesSystem.service;

import com.swp391.JewelrySalesSystem.entity.User;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
  User findByPhone(String phone);

  User findById(Long id);

  List<User> findAll();

  void deactivateStaff(Long id);

  void save(User user);
}
