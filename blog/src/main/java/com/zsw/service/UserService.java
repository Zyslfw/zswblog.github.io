package com.zsw.service;

import com.zsw.po.User;

public interface UserService {
    User checkUser(String username, String password);
}
