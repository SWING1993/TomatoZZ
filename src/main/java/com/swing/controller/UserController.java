package com.swing.controller;

import com.swing.entity.User;
import com.swing.service.UserService;
import com.swing.utils.RestResult;
import com.swing.utils.RestResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.swing.utils.JWT;
import redis.clients.jedis.Jedis;

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
    public RestResult<Map<String, Object>> login(@RequestParam(value = "email", required = true) String email, @RequestParam(value = "password", required = true) String passwprd) {
        System.out.println("用户登录");
        User user = this.userService.findUserByEmail(email);
        if (user.getPassword().equals(passwprd)) {

            String token = JWT.sign(user);

            // 将token存入redis
            Jedis jedis = new Jedis("localhost");
            System.out.println("成功连接redis");
            String idStr = String.valueOf(user.getId());
            jedis.set(idStr, token);
            System.out.println("redis 存储的字符串为: "+ jedis.get(String.valueOf(user.getId())));

            Map<String, Object> userMap = new HashMap<String ,Object>();
            userMap.put("id" ,user.getId());
            userMap.put("token", token);
            userMap.put("created", user.getCreated());
            userMap.put("email", user.getEmail());
            return RestResultGenerator.genSuccessResult(userMap);
        } else {
            return RestResultGenerator.genErrorResult("密码错误");
        }
    }

    @RequestMapping(path = "/findUser", method = RequestMethod.POST)
    public RestResult<Map<String, Object>> findUser(@RequestParam(value = "id", required = true) int id) {
        System.out.println("查询用户");
        User user = this.userService.findUserById(id);
        Map<String, Object> userMap = new HashMap<String ,Object>();
        userMap.put("id" ,user.getId());
        userMap.put("created", user.getCreated());
        userMap.put("email", user.getEmail());
        return RestResultGenerator.genSuccessResult(userMap);
    }
}
