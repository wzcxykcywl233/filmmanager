package com.example.filmmanager.model;

public class User {
    private Integer id;
    private String username;
    private String password;
    private UserRole role;
    // 其他字段（邮箱、电话等）可根据需要添加

    public User() {}

    public User(String username, String password, UserRole role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }

    public boolean hasRole(UserRole role) {
        return this.role == role;
    }
}