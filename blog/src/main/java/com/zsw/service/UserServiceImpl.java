package com.zsw.service;

import com.zsw.dao.UserRepository;
import com.zsw.po.User;
import com.zsw.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*UserService接口实现*/
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    @Autowired//注入
    private void setUserRepository(UserRepository userRepository){
        this.userRepository= userRepository;
    }
    @Override
    public User checkUser(String username, String password) {//根据用户名和密码，查到就返回user，查不到就返回null
        User user = userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }
}
