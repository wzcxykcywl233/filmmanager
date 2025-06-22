package com.example.filmmanager.service;

import com.example.filmmanager.model.User;

/**
 * 用户服务实现 - 仅实现登录所需功能
 */
public class UserServiceImpl implements UserService {

    // 模拟数据库的测试用户数据
    private static final User TEST_ADMIN = new User("admin", "admin123", true);
    private static final User TEST_USER = new User("user", "user123", false);

    @Override
    public User authenticate(String username, String password) {
        // 实际项目中应从数据库查询
        if (TEST_ADMIN.getUsername().equals(username) &&
                TEST_ADMIN.getPassword().equals(password)) {
            return TEST_ADMIN;
        }

        if (TEST_USER.getUsername().equals(username) &&
                TEST_USER.getPassword().equals(password)) {
            return TEST_USER;
        }

        return null; // 认证失败
    }

    // 以下为预留接口的空实现
    @Override
    public User getUserById(int id) {
        // 待实现
        return null;
    }

    @Override
    public void updateUser(User user) {
        // 待实现
    }

    @Override
    public void deleteUser(int id) {
        // 待实现
    }
}