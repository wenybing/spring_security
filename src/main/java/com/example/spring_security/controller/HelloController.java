package com.example.spring_security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author wenyabing
 * @Date 2019/5/15 14:31
 */
@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/admin/hello")
    public String helloAdmin() {
        return "hello admin!";
    }

    @GetMapping("/DBA/hello")
    public String helloDBA() {
        return "hello DBA";
    }

    @GetMapping("/user/hello")
    public String helloUser() {
        return "hello user!";
    }
}
