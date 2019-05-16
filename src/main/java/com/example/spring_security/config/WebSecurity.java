package com.example.spring_security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * @Author wenyabing
 * @Date 2019/5/16 15:13
 */
@Configuration
/**
 * 开启基于注解的安全配置
 */
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurity {
}
