package com.example.spring_security.custom;

import com.example.spring_security.dao.MenuMapper;
import com.example.spring_security.entity.Menu;
import com.example.spring_security.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * @Author WenYaBing
 * @Date 2019/5/16 20:29
 * 自定义筛选器调用安全元数据源
 */
public class CustomFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    private AntPathMatcher antPathMatcher = new AntPathMatcher();
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        //提取当前请求的url
        String requestUrl = ((FilterInvocation) o).getRequestUrl();
        //从数据库中获取资源信息，在实际开发环境中可以将资源信息保存在缓存中或缓存数据库中
        List<Menu> menus = menuMapper.getAllMenus();
        /**
         * 遍历资源信息，遍历过程中获取当前请求的 URL 所需要的角色信息并返回。如
         * 果当前请求的 URL 在资源表中不存在相应的模式，就假设该请求登录后即可访问，即直接返
         * ROLE_LOGIN
         */
        for (Menu menu : menus) {
            if (antPathMatcher.match(menu.getPattern(), requestUrl)) {
                List<Role> roles = menu.getRoles();
                String[] roleArray = new String[roles.size()];
                for (int i = 0; i < roles.size(); i++) {
                    roleArray[i] = roles.get(i).getRole();
                }
                return SecurityConfig.createList(roleArray);
            }
        }
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    /**
     * 用来返回所有定义好的权限资源， Spring Security 在启动时会校验
     * 相关配置是否正确 ，如果 需要校验，那么该方法直接返回 null 即可
     */
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    /**
     * 方法返回类对象是否支持校验。
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }
}
