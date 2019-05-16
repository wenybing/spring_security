package com.example.spring_security.dao;

import com.example.spring_security.entity.Role;
import com.example.spring_security.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author wenyabing
 * @Date 2019/5/16 17:05
 */
@Mapper
public interface UserMapper {
    /**
     * 根据用户名查找用户信息
     *
     * @param username
     * @return
     */
    User loadUserByUsername(String username);

    /**
     * 根据用户id查询用户角色
     *
     * @param uid
     * @return
     */
    List<Role> getUserRolesByUid(Integer uid);
}
