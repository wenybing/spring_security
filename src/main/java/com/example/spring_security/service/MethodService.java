package com.example.spring_security.service;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

/**
 * @Author wenyabing
 * @Date 2019/5/16 15:18
 */
@Service
public class MethodService {
    @PreAuthorize("hasAnyRole('ADMIN','USER','DBA')")
    public String helloUser() {
        return "hello user!";
    }

    @PostAuthorize("hasRole('DBA')")
    public String helloDBA() {
        return "hello DBA";
    }

    @Secured("ROLE_ADMIN")
    public String helloAdmin() {
        return "hello admin!";
    }
}
