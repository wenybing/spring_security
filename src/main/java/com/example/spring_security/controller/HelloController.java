package com.example.spring_security.controller;

import com.example.spring_security.service.MethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author wenyabing
 * @Date 2019/5/15 14:31
 */
@RestController
public class HelloController {
    @Autowired
    private MethodService methodService;

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/admin/hello")
    public String helloAdmin() {
        return methodService.helloAdmin();
    }

    @GetMapping("/db/hello")
    public String helloDBA() {
        return methodService.helloDBA();
    }

    @GetMapping("/user/hello")
    public String helloUser() {
        return methodService.helloUser();
    }

    @GetMapping("/login_page")
    public ModelAndView login() {
        return new ModelAndView("login_page");
    }
}
