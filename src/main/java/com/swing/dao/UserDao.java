package com.swing.dao;
import com.swing.entity.User;

public interface UserDao {

    void register(User user);

    User findUserById(int id);

    User findUserByEmail(String email);

}
