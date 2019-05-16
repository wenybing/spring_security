package com.example.spring_security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringSecurityApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void testPasswordEncode() {
        String password = "123";
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode1 = passwordEncoder.encode(password);
        String encode2 = passwordEncoder.encode(password);
        String encode3 = passwordEncoder.encode(password);
        String encode4 = passwordEncoder.encode(password);
        String encode5 = passwordEncoder.encode(password);
        String encode6 = passwordEncoder.encode("root");
        System.out.println(encode1);
        System.out.println(encode2);
        System.out.println(encode3);
        System.out.println(encode4);
        System.out.println(encode5);
        System.out.println(encode6);

    }


}
