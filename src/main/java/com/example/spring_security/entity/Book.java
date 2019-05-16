package com.example.spring_security.entity;

import lombok.Data;

/**
 * @Author wenyabing
 * @Date 2019/5/16 11:36
 */
@Data
public class Book {
    /**
     * id
     */
    private Integer id;
    /**
     * 书名
     */
    private String name;
    /**
     * 作者
     */
    private String author;
}
