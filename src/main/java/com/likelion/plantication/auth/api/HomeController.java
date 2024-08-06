package com.likelion.plantication.auth.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")  // 기본 URL을 지정합니다.
public class HomeController {

    @GetMapping
    public ResponseEntity<Void> home() {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
