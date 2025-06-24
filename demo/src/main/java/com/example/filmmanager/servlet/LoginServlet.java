package com.example.filmmanager.servlet;

import com.example.filmmanager.model.User;
import com.example.filmmanager.model.UserRole;
import com.example.filmmanager.service.UserService;
import com.example.filmmanager.service.impl.UserServiceImpl;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {

    private UserService userService ;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. 获取请求参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String verifycode = request.getParameter("verifycode");

        // 2. 验证码校验
        HttpSession session = request.getSession();
        String storedCode = (String) session.getAttribute("verifyCode");
        session.removeAttribute("verifyCode"); // 一次性验证码

        if (storedCode == null || !storedCode.equalsIgnoreCase(verifycode)) {
            request.setAttribute("login_msg", "验证码错误或已过期");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        // 3. 用户认证
        User user = userService.authenticate(username, password);

        if (user != null) {
            // 4. 登录成功处理 - 使用新版本角色判断
            session.setAttribute("currentUser", user); // 统一存储当前用户

            // 根据角色重定向
            if (user.getRole() == UserRole.ADMINISTRATOR) {
                response.sendRedirect("admin/dashboard.jsp");
            } else {
                response.sendRedirect("user/dashboard.jsp");
            }
        } else {
            // 5. 登录失败处理
            request.setAttribute("login_msg", "用户名或密码错误");
            request.getRequestDispatcher("/login.html").forward(request, response);
        }
    }
}