package com.example.filmmanager.controller.admin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/")
    public String home() {
        return "Film Manager Application is running!";
    }

    @GetMapping("/ping")
    public String ping() {
        return "Application is alive! " + System.currentTimeMillis();
    }
}