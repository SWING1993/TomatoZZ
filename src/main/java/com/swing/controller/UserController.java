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
import com.swing.utils.Md5;
import com.swing.utils.JWT;
import redis.clients.jedis.Jedis;

@ResponseBody
@Controller
@RequestMapping("/user")
public class UserController {

    // 盐值，用于混淆
    private final static String passwordSalt = "tomato00zz321mmf";

    @Resource
    private UserService userService;

    @RequestMapping(path = "/register",method = RequestMethod.POST)
    public RestResult<User> register(@RequestParam(value = "email", required = false) String email, @RequestParam(value = "password", required = true) String password) {
        System.out.println("用户注册");
        User user = new User();
        user.setEmail(email);
        user.setPassword(Md5.getMd5(password, passwordSalt));
        user.setCreated(new Date());
        this.userService.register(user);
        return RestResultGenerator.genSuccessResult();
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public RestResult<Map<String, Object>> login(@RequestParam(value = "email", required = true) String email,
                                                 @RequestParam(value = "password", required = true) String password) {
        System.out.println("用户登录");
        User user = this.userService.findUserByEmail(email);
        if (user.getPassword().equals(Md5.getMd5(password, passwordSalt))) {
            String token = JWT.sign(user);
            // 将token存入redis
            Jedis jedis = new Jedis("localhost");
            System.out.println("成功连接redis");
            String idStr = String.valueOf(user.getId());
            jedis.set(idStr, token);
            System.out.println("redis 存储的字符串为: "+ jedis.get(idStr));

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

    @RequestMapping(path = "/findById", method = RequestMethod.GET)
    public RestResult<User> findUser(@RequestParam(value = "id", required = true) int id) {
        System.out.println("查询用户");
        User user = this.userService.findUserById(id);
        return RestResultGenerator.genSuccessResult(user);
    }

    @RequestMapping(path = "/update", method = RequestMethod.PUT)
    public RestResult<User> update(@RequestHeader("uid") int uid,
                                   @RequestParam (value = "sex", required = false) Integer sex,
                                   @RequestParam (value = "nickname", required = false) String nickname,
                                   @RequestParam (value = "avatarUrl", required = false) String avatarUrl,
                                   @RequestParam (value = "userDesc", required = false) String userDesc) {


        User user = this.userService.findUserById(uid);
        int sexValue = 0;
        if (sex != null && (sex == 0 ||sex == 1 ||sex == 2) ) {
            sexValue = sex;
        }
        user.setSex(sexValue);
        user.setNickname(nickname);
        user.setAvatarUrl(avatarUrl);
        user.setUserDesc(userDesc);
        user.setUpdated(new Date());
        System.out.println("更新用户资料" + user);
        this.userService.update(user);
        return RestResultGenerator.genSuccessResult(user);
    }

    @RequestMapping(path = "/info", method = RequestMethod.GET)
    public RestResult<User> info(@RequestHeader("uid") int uid) {
        User user = this.userService.findUserById(uid);
        return RestResultGenerator.genSuccessResult(user);
    }
}
