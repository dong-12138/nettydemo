package com.briup.impl;

import com.briup.dto.User;
import com.briup.interfaces.UserService;

public class UserServiceImpl implements UserService {
    public User login(User user) {
        user.setPwd(null);
        user.setType("login success");
        return user;
    }
}
