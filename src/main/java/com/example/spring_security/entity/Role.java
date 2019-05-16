package com.example.spring_security.entity;

import lombok.Data;

/**
 * @Author wenyabing
 * @Date 2019/5/16 16:51
 */
@Data
public class Role {
    /**
     * 角色id
     */
    private Integer id;
    /**
     * 角色
     */
    private String role;
    /**
     * 角色名称
     */
    private String roleName;

}
