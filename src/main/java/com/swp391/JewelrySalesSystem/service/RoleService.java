package com.swp391.JewelrySalesSystem.service;

import com.swp391.JewelrySalesSystem.entity.Role;
import com.swp391.JewelrySalesSystem.enums.RoleUser;

public interface RoleService {
  Role findRole(RoleUser name);
}
