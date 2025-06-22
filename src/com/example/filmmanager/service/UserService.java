package com.example.filmmanager.service;

import com.example.filmmanager.model.User;

/**
 * 用户服务接口
 * 其他功能只需预留接口，实际实现可后续完成
 */
public interface UserService {
    /**
     * 用户认证方法
     * @param username 用户名
     * @param password 密码
     * @return 认证成功的用户对象，失败返回null
     */
    User authenticate(String username, String password);

    /**
     * 以下为预留接口，供其他功能使用
     */
    User getUserById(int id); // 预留接口
    void updateUser(User user); // 预留接口
    void deleteUser(int id); // 预留接口
}