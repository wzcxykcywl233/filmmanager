package com.example.filmmanager.service;

import com.example.filmmanager.model.Permission;
import com.example.filmmanager.model.User;
import com.example.filmmanager.model.UserRole;

import java.util.Set;

public interface AuthorizationService {
    /**
     * 检查用户是否拥有指定权限
     * @param user 当前用户
     * @param permission 需要验证的权限
     * @return 是否拥有权限
     */
    boolean hasPermission(User user, Permission permission);

    /**
     * 获取用户角色权限映射（预留接口）
     * @param role 用户角色
     * @return 权限集合
     */
    Set<Permission> getPermissionsForRole(UserRole role);
}