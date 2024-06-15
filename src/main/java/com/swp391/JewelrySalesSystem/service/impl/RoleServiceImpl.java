package com.swp391.JewelrySalesSystem.service.impl;

import com.swp391.JewelrySalesSystem.entity.Role;
import com.swp391.JewelrySalesSystem.enums.ErrorCode;
import com.swp391.JewelrySalesSystem.enums.RoleUser;
import com.swp391.JewelrySalesSystem.exception.RoleException;
import com.swp391.JewelrySalesSystem.repository.RoleRepository;
import com.swp391.JewelrySalesSystem.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {
  private final RoleRepository roleRepository;

  @Override
  public Role findRole(RoleUser name) {
    return roleRepository
        .findByName(name)
        .orElseThrow(() -> new RoleException(ErrorCode.ROLE_NOT_FOUND));
  }
}
