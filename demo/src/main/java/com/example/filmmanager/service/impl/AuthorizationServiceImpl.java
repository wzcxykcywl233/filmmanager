package com.example.filmmanager.service.impl;

import com.example.filmmanager.model.Permission;
import com.example.filmmanager.model.User;
import com.example.filmmanager.model.UserRole;
import com.example.filmmanager.service.AuthorizationService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {
    // 角色-权限映射配置
    private static final Map<UserRole, Set<Permission>> ROLE_PERMISSIONS = new HashMap<>();

    static {
        // 普通用户权限
        Set<Permission> userPermissions = EnumSet.of(
                Permission.MOVIE_BROWSE,
                Permission.MOVIE_SEARCH,
                Permission.TICKET_PURCHASE
        );

        // 管理员权限（包含普通用户所有权限+管理权限）
        Set<Permission> adminPermissions = EnumSet.copyOf(userPermissions);
        adminPermissions.addAll(EnumSet.of(
                Permission.USER_MANAGE,
                Permission.MOVIE_MANAGE,
                Permission.ORDER_MANAGE
        ));

        ROLE_PERMISSIONS.put(UserRole.REGULAR_USER, userPermissions);
        ROLE_PERMISSIONS.put(UserRole.ADMINISTRATOR, adminPermissions);
    }

    @Override
    public boolean hasPermission(User user, Permission permission) {
        if (user == null) return false;
        Set<Permission> permissions = ROLE_PERMISSIONS.get(user.getRole());
        return permissions != null && permissions.contains(permission);
    }

    @Override
    public Set<Permission> getPermissionsForRole(UserRole role) {
        return Collections.unmodifiableSet(
                ROLE_PERMISSIONS.getOrDefault(role, Collections.emptySet())
        );
    }
}