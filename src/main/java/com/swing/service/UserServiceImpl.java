package com.swing.service;

import com.swing.entity.User;
import com.swing.dao.UserDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    public User findUserById(int id) {
        return userDao.findUserById(id);
    }

    public void register(User user) {
        userDao.register(user);
    };


}
