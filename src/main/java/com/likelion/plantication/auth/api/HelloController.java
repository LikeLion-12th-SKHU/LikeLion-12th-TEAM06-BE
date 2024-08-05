package com.likelion.plantication.auth.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/api/demo-web")
    public String hello() {
        return "테스트입니다.";
    }
}
