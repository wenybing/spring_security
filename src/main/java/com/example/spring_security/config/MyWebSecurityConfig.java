package com.example.spring_security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author wenyabing
 * @Date 2019/5/15 18:09
 */
@Configuration
public class MyWebSecurityConfig extends WebSecurityConfigurerAdapter {
    /*@Bean
    PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }*/

    /**
     * 密码加密
     *
     * @return
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("admin").password("$2a$10$Tgj7SYjbvRNe0yLgdNyocejhnvcCNpjknqY7cjvC3NfgITOscISr6").roles("ADMIN")
                .and()
//                .withUser("lisi").password("123").roles("DBA");
                .withUser("lisi").password("$2a$10$vUlO./MiALLX6/vu3JS9guBBs7xDW6O.35vSzr57OGDvfZ3bLwwn2").roles("DBA");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/*").hasAnyRole("ADMIN")
                .antMatchers("/user/*").access("hasAnyRole('ADMIN','USER')")
                .antMatchers("/db/*").access("hasAnyRole('ADMIN') and hasRole('DBA')")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
//                .loginPage("/login_page")
//                .loginProcessingUrl("/login")
//                .usernameParameter("username")
//                .passwordParameter("password")
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                        /**
                         *获取当前登录用户的信息
                         */
                        Object principal = authentication.getPrincipal();
                        httpServletResponse.setContentType("application/json;charset=utf-8");
                        httpServletResponse.setStatus(200);
                        Map<String, Object> map = new HashMap<>();
                        map.put("status", 200);
                        map.put("message", principal);
                        ObjectMapper objectMapper = new ObjectMapper();
                        String string = objectMapper.writeValueAsString(map);
                        PrintWriter printWriter = httpServletResponse.getWriter();
                        printWriter.write(string);
                        printWriter.flush();
                        printWriter.close();

                    }
                })
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest httpServletRequest,
                                                        HttpServletResponse httpServletResponse,
                                                        AuthenticationException e) throws IOException, ServletException {
                        httpServletResponse.setContentType("application/json;charset=utf-8");
                        httpServletResponse.setStatus(401);
                        Map<String, Object> map = new HashMap<>();
                        map.put("status", 401);
                        if (e instanceof LockedException) {
                            map.put("message", "账户被锁定，登录失败！");
                        } else if (e instanceof BadCredentialsException) {
                            map.put("message", "用户名或密码错误，登录失败！");
                        } else if (e instanceof DisabledException) {
                            map.put("message", "账户被禁用，登录失败！");
                        } else if (e instanceof AccountExpiredException) {
                            map.put("message", "账户已过期，登录失败！");
                        } else if (e instanceof CredentialsExpiredException) {
                            map.put("message", "密码已过期，登录失败！");
                        } else {
                            map.put("message", "登录失败!");
                        }
                        ObjectMapper objectMapper = new ObjectMapper();
                        String string = objectMapper.writeValueAsString(map);
                        PrintWriter printWriter = httpServletResponse.getWriter();
                        printWriter.write(string);
                        printWriter.flush();
                        printWriter.close();
                    }
                })
                .and()
                .logout()
                .logoutUrl("/logout")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .addLogoutHandler(new LogoutHandler() {
                    @Override
                    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {

                    }
                })
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                        httpServletResponse.sendRedirect("/login_page");
                    }
                })
                .permitAll()
                .and()
                .csrf()
                .disable();
    }
}