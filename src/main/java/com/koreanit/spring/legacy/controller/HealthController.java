package com.koreanit.spring.legacy.controller;

import java.time.LocalDateTime;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/health")
    public String health() {
        return LocalDateTime.now().toString();
    }

    @GetMapping("/health/check")
    public String check(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int count) {
        System.out.println("[Controller] name = " + name);
        System.out.println("[Controller] count = " + count);

        return "name=" + name + ", count=" + count;
    }
}