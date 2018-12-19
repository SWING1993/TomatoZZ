package com.swing.service;
import com.swing.entity.User;

public interface UserService {

    // 根据 id 寻找对应的 User
    public User findUserById(int id);
}
