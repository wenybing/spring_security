package com.example.spring_security.service;

import org.springframework.stereotype.Service;

/**
 * @Author wenyabing
 * @Date 2019/5/16 15:18
 */
@Service
public class MethodService {
    //    @PreAuthorize("hasAnyRole('ADMIN','USER','DBA')")
    public String helloUser() {
        return "hello user!";
    }

    //    @PostAuthorize("hasAnyRole('ADMIN','DBA')")
    public String helloDBA() {
        return "hello DBA";
    }

    //    @Secured("ROLE_ADMIN")
    public String helloAdmin() {
        return "hello admin!";
    }
}
