package com.swing.controller;

import com.swing.entity.User;
import com.swing.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@ResponseBody
@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping(path = "/register",method = RequestMethod.POST)
    public String register(@RequestParam(value = "email", required = false) String password, @RequestParam(value = "password", required = true) String email) {
        return password + email;
    }

    @RequestMapping(path = "/findUser", method = RequestMethod.GET)
    public User findUser(@RequestParam(value = "id", required = true) int id) {
        User user = this.userService.findUserById(id);
        return user;
    }

}
