package com.swing.service;
import com.swing.entity.User;

public interface UserService {

    // 注册用户
    public void register(User user);

    // 根据 id 寻找对应的 User
    public User findUserById(int id);

    // 根据 email 寻找对应的 User
    public User findUserByEmail(String email);
}
