package com.example.spring_security.config;

import com.example.spring_security.custom.CustomAccessDecisionManager;
import com.example.spring_security.custom.CustomFilterInvocationSecurityMetadataSource;
import com.example.spring_security.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
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
    @Autowired
    private UserService userService;

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
//        auth.inMemoryAuthentication().withUser("admin").password("$2a$10$K0uU50HPrlkFdij/vVdckuV2Waja3Wza6Jb3YulDk/f51.7tEjumG").roles("ADMIN")
//                .and()
//                .withUser("root").password("$2a$10$K0uU50HPrlkFdij/vVdckuV2Waja3Wza6Jb3YulDk/f51.7tEjumG").roles("DBA")
//                .and()
//                .withUser("user").password("$2a$10$K0uU50HPrlkFdij/vVdckuV2Waja3Wza6Jb3YulDk/f51.7tEjumG").roles("USER");
        auth.userDetailsService(userService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                /*.antMatchers("/admin/*").hasAnyRole("ADMIN")
                .antMatchers("/user/*").access("hasAnyRole('ADMIN','DBA','USER')")
                .antMatchers("/db/*").access("hasAnyRole('ADMIN','DBA')")
                .anyRequest()
                .authenticated()*/
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setSecurityMetadataSource(filterInvocationSecurityMetadataSource());
                        o.setAccessDecisionManager(accessDecisionManager());
                        return o;
                    }
                })
                .and()
                .formLogin()
//                .loginPage("/login_page")
                .loginProcessingUrl("/login")
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


    /**
     * 角色继承
     *
     * @return
     */
    @Bean
    RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String hierarchy = "ROLE_ADMIN > ROLE_DBA ROLE_ABD >ROLE_USER";
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }

    @Bean
    FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource() {
        return new CustomFilterInvocationSecurityMetadataSource();
    }

    @Bean
    AccessDecisionManager accessDecisionManager() {
        return new CustomAccessDecisionManager();
    }
}