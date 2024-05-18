package com.swp391.JewelrySalesSystem.service.impl;

import com.swp391.JewelrySalesSystem.entity.Role;
import com.swp391.JewelrySalesSystem.enums.RoleUser;
import com.swp391.JewelrySalesSystem.repository.RoleRepository;
import com.swp391.JewelrySalesSystem.service.RoleService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {
  private final RoleRepository roleRepository;

  @Override
  public Optional<Role> findRole(RoleUser name) {
    return roleRepository.findByName(name);
  }
}
