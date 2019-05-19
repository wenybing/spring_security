package com.example.spring_security.entity;

import lombok.Data;

/**
 * @Author wenyabing
 * @Date 2019/5/16 20:21
 */
@Data
public class MenuRole {
    private Integer id;
    /**
     * 菜单id
     */
    private Integer mid;
    /**
     * 角色id
     */
    private Integer rid;
}
