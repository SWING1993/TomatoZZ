package com.swing.controller;

import com.swing.entity.User;
import com.swing.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;


@ResponseBody
@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping(path = "/register",method = RequestMethod.POST)
    public String register(@RequestParam(value = "email", required = false) String email, @RequestParam(value = "password", required = true) String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setCreated(new Date());
        this.userService.register(user);
        return email;
    }

    @RequestMapping(path = "/findUser", method = RequestMethod.GET)
    public User findUser(@RequestParam(value = "id", required = true) int id) {
        User user = this.userService.findUserById(id);
        return user;
    }

}
