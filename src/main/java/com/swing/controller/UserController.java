package com.swing.controller;

import com.swing.entity.User;
import com.swing.service.UserService;
import com.swing.utils.RestResult;
import com.swing.utils.RestResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import com.swing.utils.JWT;

@ResponseBody
@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping(path = "/register",method = RequestMethod.POST)
    public RestResult<User> register(@RequestParam(value = "email", required = false) String email, @RequestParam(value = "password", required = true) String password) {
        System.out.println("用户注册");
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setCreated(new Date());
        this.userService.register(user);
        return RestResultGenerator.genSuccessResult();
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public RestResult<User> login(@RequestParam(value = "email", required = true) String email, @RequestParam(value = "password", required = true) String passwprd) {
        System.out.println("用户登录");
        User user = this.userService.findUserByEmail(email);
        if (user.getPassword().equals(passwprd)) {
            String token = JWT.sign(user, 60L* 1000L* 30L);
            user.setToken(token);
            return RestResultGenerator.genSuccessResult(user);
        } else {
            return RestResultGenerator.genErrorResult("密码错误");
        }
    }

    @RequestMapping(path = "/findUser", method = RequestMethod.POST)
    public RestResult<User> findUser(@RequestParam(value = "id", required = true) int id) {
        System.out.println("查询用户");
        User user = this.userService.findUserById(id);
        return RestResultGenerator.genSuccessResult(user);
    }

}
