package com.swing.dao;
import com.swing.entity.User;

public interface UserDao {

    User findUserById(int id);

    void register(User user);

}
