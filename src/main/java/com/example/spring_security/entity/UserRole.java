package com.example.spring_security.entity;

import lombok.Data;

/**
 * @Author wenyabing
 * @Date 2019/5/16 16:53
 */
@Data
public class UserRole {

    private Integer id;
    /**
     * 用户id
     */
    private Integer uid;
    /**
     * 角色id
     */
    private Integer rid;
}
