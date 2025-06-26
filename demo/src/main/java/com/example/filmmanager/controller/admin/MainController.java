package com.example.filmmanager.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// 新增 DashboardController.java
@Controller
public class MainController {
    @Controller
    public class HomeController {

        @GetMapping("/")
        public String home() {
            return "redirect:/login.html";
        }

        @GetMapping("/login.html")
        public String loginPage() {
            return "login"; // 对应login.html模板
        }
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "admin/dashboard"; // 对应模板路径
    }

    @GetMapping("/user/dashboard")
    public String userDashboard() {
        return "user/dashboard"; // 对应模板路径
    }
}