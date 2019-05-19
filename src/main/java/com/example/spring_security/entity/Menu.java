package com.example.spring_security.entity;

import lombok.Data;

import java.util.List;

/**
 * @Author wenyabing
 * @Date 2019/5/16 20:20
 */
@Data
public class Menu {
    private String id;
    /**
     * 匹配路径
     */
    private String pattern;
    /**
     * 角色
     */
    private List<Role> roles;
}
