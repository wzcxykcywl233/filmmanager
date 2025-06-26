package com.example.filmmanager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EndToEndTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port;
    }

    @Test
    void testPublicEndpointAccessible() {
        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl + "/", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("Film Manager Application is running!"));
    }

    @Test
    void testAdminAccessToProtectedResource() {
        // 1. 登录管理员账户
        HttpHeaders headers = loginAndGetSessionHeaders("admin", "admin123");

        // 2. 访问管理员资源
        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl + "/admin/users",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                String.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testRegularUserAccessDeniedToAdminResource() {
        // 1. 登录普通用户
        HttpHeaders headers = loginAndGetSessionHeaders("user", "user123");

        // 2. 尝试访问管理员资源
        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl + "/admin/users",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                String.class
        );

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertTrue(response.getBody().contains("权限不足"));
    }

    @Test
    void testUnauthenticatedUserRedirectedToLogin() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                baseUrl + "/admin/users",
                String.class
        );

        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getHeaders().getLocation()).toString().contains("/login"));
    }

    private HttpHeaders loginAndGetSessionHeaders(String username, String password) {
        // 准备登录表单数据
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("username", username);
        formData.add("password", password);

        // 发送登录请求
        ResponseEntity<String> loginResponse = restTemplate.postForEntity(
                baseUrl + "/login",
                new HttpEntity<>(formData, new HttpHeaders()),
                String.class
        );

        // 获取会话Cookie
        String sessionCookie = loginResponse.getHeaders().getFirst(HttpHeaders.SET_COOKIE);

        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        if (sessionCookie != null) {
            headers.add(HttpHeaders.COOKIE, sessionCookie);
        }
        return headers;
    }
}