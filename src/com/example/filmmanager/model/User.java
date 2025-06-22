package com.example.filmmanager.model;

public class User {
    private Integer id;
    private String username;
    private String password;
    private Boolean isAdmin;
    // 其他字段（邮箱、电话等）可根据需要添加

    public User() {}

    public User(String username, String password, Boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Boolean isAdmin() { return isAdmin; }
    public void setIsAdmin(Boolean admin) { isAdmin = admin; }
}