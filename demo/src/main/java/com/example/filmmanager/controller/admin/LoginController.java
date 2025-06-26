package com.example.filmmanager.controller.admin;

import com.example.filmmanager.model.User;
import com.example.filmmanager.model.UserRole;
import com.example.filmmanager.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session) {
        User user = userService.authenticate(username, password);
        if (user != null) {
            session.setAttribute("currentUser", user);
            //session.setAttribute("userRole", user.getRole().name());
            return "redirect:/"+ (user.getRole() == UserRole.ADMINISTRATOR ? "admin" : "user") + "/dashboard";
        }
        return "redirect:/login?error";
    }
}