package com.example.filmmanager.filter;

import com.example.filmmanager.model.Permission;
import com.example.filmmanager.model.User;
import com.example.filmmanager.service.AuthorizationService;
import com.example.filmmanager.service.impl.AuthorizationServiceImpl;
import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Order(1)
public class AuthorizationFilter implements Filter {
    @Autowired
    private AuthorizationService authService;

    // URL-权限映射
    private static final Map<String, Permission> URL_PERMISSIONS = new HashMap<>();

    static {
        // 普通用户权限
        URL_PERMISSIONS.put("/film/browse", Permission.MOVIE_BROWSE);
        URL_PERMISSIONS.put("/film/search", Permission.MOVIE_SEARCH);
        URL_PERMISSIONS.put("/ticket/purchase", Permission.TICKET_PURCHASE);

        // 管理员权限
        URL_PERMISSIONS.put("/admin/users", Permission.USER_MANAGE);
        URL_PERMISSIONS.put("/admin/films", Permission.MOVIE_MANAGE);
        URL_PERMISSIONS.put("/admin/orders", Permission.ORDER_MANAGE);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String contextPath = httpRequest.getContextPath();
        String path = httpRequest.getRequestURI().substring(contextPath.length());
        // 不需要权限的路径直接放行
        if (path.startsWith("/static/") || path.equals("/login")) {
            chain.doFilter(request, response);
            return;
        }

        Permission requiredPermission = URL_PERMISSIONS.get(path);
        if (requiredPermission == null) {
            // 默认拒绝未配置的敏感路径
            if (path.startsWith("/admin") || path.startsWith("/user")) {
                handleUnauthorized(httpRequest, httpResponse);
                return;
            }
            chain.doFilter(request, response);
            return;
        }

        // 检查用户权限
        HttpSession session = httpRequest.getSession(false);
        User user = session != null ?
                (User) session.getAttribute("currentUser") : null;

        if (user == null) {
            // 未登录用户
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.html");
            return;
        }

        if (!authService.hasPermission(user, requiredPermission)) {
            // 权限不足处理
            handleUnauthorized(httpRequest, httpResponse);
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

    private void handleUnauthorized(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        // AJAX请求返回JSON错误
        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("{\"error\":\"权限不足\"}");
        } else {
            // 普通请求重定向到错误页
            response.sendRedirect(request.getContextPath() + "/error/403.html");
        }
    }


}