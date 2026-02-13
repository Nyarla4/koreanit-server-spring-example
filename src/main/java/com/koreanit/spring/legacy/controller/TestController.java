package com.koreanit.spring.legacy.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test/param")
    public Map<String, Object> param(@RequestParam String msg) {
        Map<String, Object> result = new HashMap<>();
        result.put("msg", msg);
        return result;
    }

    @GetMapping("/test/page")
    public Map<String, Object> page(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Map<String, Object> result = new HashMap<>();
        result.put("page", page);
        result.put("size", size);
        return result;
    }
    
    @GetMapping("/test/optional")
    public Map<String, Object> optional(
            @RequestParam(required = false) String keyword
    ) {
        Map<String, Object> result = new HashMap<>();
        result.put("keyword", keyword);
        return result;
    }
    
    @GetMapping("/test/path/{value}")
    public Map<String, Object> path(@PathVariable int value) {
        Map<String, Object> result = new HashMap<>();
        result.put("value", value);
        return result;
    }

    @GetMapping("/test/header")
    public Map<String, Object> header(
            @RequestHeader(required = false, name = "User-Agent") String userAgent
    ) {
        Map<String, Object> result = new HashMap<>();
        result.put("userAgent", userAgent);
        return result;
    }

    @PostMapping("/test/body")
    public Map<String, Object> body(@RequestBody Map<String, Object> body) {
        Map<String, Object> result = new HashMap<>();
        result.put("received", body);
        return result;
    }

    @PostMapping("/test/body-dto")//Mao대신 TestBodyRequest
    public Map<String, Object> bodyDto(@RequestBody TestBodyRequest req) {
        Map<String, Object> result = new HashMap<>();
        result.put("a", req.getA());
        result.put("b", req.getB());
        return result;
    }
}
