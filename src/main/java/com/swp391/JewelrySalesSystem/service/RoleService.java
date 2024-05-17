package com.swp391.JewelrySalesSystem.service;

import com.swp391.JewelrySalesSystem.entity.Role;
import com.swp391.JewelrySalesSystem.enums.RoleUser;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findRole(RoleUser name);
}
