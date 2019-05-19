package com.example.spring_security.custom;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @Author wenyabing
 * @Date 2019/5/17 11:55
 * 自定义访问决策管理
 */
public class CustomAccessDecisionManager implements AccessDecisionManager {
    /**
     * 自定义 AccessDecisionManager 并重写 decide 方法，在该方法中判断当前登录的用户是否具
     * 备当前请求 URL 所需要的角色信息，如果不具备，就抛出 AccessDeniedException 异常，否
     * 则不做任何事即可
     * <p>
     * decide 方法有三个参数 authentication，object，configAttributes
     * 第一个参数包含当前登录用户的信息；
     * 第 二个参数则是一个FilterInvocation ，可以获取当前请求对象等；
     * 第 三个参数就是FilterInvocationSecurityMetadataSource 中的getAttributes 方法的返回值即当前请求URL需要的角色。
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        /**
         * 行进行角色信息对比，如果需要的角色是 ROLE_LOGIN肘，说明当前请求的 URL
         * 户登录后即可访问，如果 auth UsemamePasswordAuthenticationToken 的实例 ，那么说明
         * 前用户 登录，该方法到此结束，否则进入 的判断流程，如果当前用户具备当前请求
         * 要的角色，那么方法结束。
         */
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (ConfigAttribute configAttribute : configAttributes) {
            if ("ROLE_LOGIN".equals(configAttribute.getAttribute()) && authentication instanceof UsernamePasswordAuthenticationToken) {
                return;
            }
            for (GrantedAuthority authority : authorities) {
                if (configAttribute.getAttribute().equals(authority.getAuthority())) {
                    return;
                }
            }
        }
        throw new AccessDeniedException("权限不足！");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
