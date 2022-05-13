package com.java.myblogproject.service;

import com.java.myblogproject.entity.User;
import com.java.myblogproject.user.CrmUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User findByUserName(String userName);

    void save(CrmUser crmUser);
}
