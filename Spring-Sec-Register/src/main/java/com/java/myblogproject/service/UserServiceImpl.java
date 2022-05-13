package com.java.myblogproject.service;

import com.java.myblogproject.dao.RoleDao;
import com.java.myblogproject.dao.UserDao;
import com.java.myblogproject.entity.Role;
import com.java.myblogproject.entity.User;
import com.java.myblogproject.user.CrmUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDAO;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User findByUserName(String userName) {

        return userDAO.findByUserName(userName);
    }

    @Override
    @Transactional
    public void save(CrmUser crmUser) {
        User user=new User();
        user.setUserName(crmUser.getUserName());
        user.setPassword(passwordEncoder.encode(crmUser.getPassword()));
        user.setFirstName(crmUser.getFirstName());
        user.setLastName(crmUser.getLastName());
        user.setEmail(crmUser.getEmail());
        user.setRoles(Arrays.asList(roleDao.findRoleByName("ROLE_EMPLOYEE")));

        userDAO.save(user);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
       User user=userDAO.findByUserName(s);
       if(user==null){
           throw new UsernameNotFoundException("invalid username or password!");
       }


        return new org.springframework.security.core.userdetails.User(user.getUserName(),
                user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
