package com.java.myblogproject.dao;


import com.java.myblogproject.entity.User;

public interface UserDao {

    public User findByUserName(String userName);
    
    public void save(User user);
    
}
