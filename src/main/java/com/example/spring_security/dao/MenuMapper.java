package com.example.spring_security.dao;

import com.example.spring_security.entity.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: wenyabing
 * @Date: 2019/5/16 20:31
 */
@Mapper
public interface MenuMapper {
    /**
     * 获取所有菜单
     *
     * @return
     */
    List<Menu> getAllMenus();
}
