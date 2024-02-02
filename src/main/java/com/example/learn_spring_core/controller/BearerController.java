package com.example.learn_spring_core.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bearer")
public class BearerController {

    @GetMapping("/check")
    public boolean checkBearer() {
        return true;
    }
}
