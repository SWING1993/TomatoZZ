package com.swing.controller;

import com.swing.entity.User;
import com.swing.service.UserService;
import com.swing.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import redis.clients.jedis.Jedis;

@ResponseBody
@Controller
@RequestMapping("/user")
public class UserController {

    // 盐值，用于混淆
    private final static String passwordSalt = "tomato00zz321mmf";

    @Autowired
    private Jedis jedis;

    @Resource
    private UserService userService;


    @RequestMapping(path = "/register",method = RequestMethod.POST)
    public RestResult<User> register(@RequestParam(value = "phone", required = false) String phone, @RequestParam(value = "password", required = true) String password) {
        System.out.println("用户注册");
        User user = new User();
        user.setPhone(phone);
        user.setPassword(Md5.getMd5(password, passwordSalt));
        user.setCreated(new Date());
        this.userService.register(user);
        return RestResultGenerator.genSuccessResult();
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public RestResult<User> login(@RequestParam(value = "phone", required = true) String phone,
                                  @RequestParam(value = "password", required = true) String password) throws IOException {
        System.out.println("用户登录");
        try {
            User user = this.userService.findUserByPhone(phone);
            password = Md5.getMd5(password, passwordSalt);
            if (user.getPassword().equals(password)) {
                String idStr = String.valueOf(user.getId());
                String token = JWT.sign(idStr);
                jedis.set(idStr, token);
                System.out.println("redis 存储的字符串为: "+ jedis.get(idStr));
                user.setToken(token);
                String botMsg = user.getPhone() + " 登录";
                DingChatBot.sendMsg(botMsg);
                return RestResultGenerator.genSuccessResult(user);
            } else {
                return RestResultGenerator.genErrorResult("密码错误");
            }
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            return RestResultGenerator.genErrorResult("用户名或密码错误");
        }
    }

    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public RestResult<Long> logout(@RequestHeader("uid") int uid) {
        System.out.println("用户退出登录");
        // 将token存入redis
        Long result = jedis.del(String.valueOf(uid));
        return RestResultGenerator.genSuccessResult(result);
    }

    public RestResult<Map<String, Object>> refreshToekn(@RequestHeader("uid") int uid, @RequestHeader("token") String token) {
        System.out.println("用户刷新token");
        String idStr = String.valueOf(uid);
        if (token.equals(jedis.get(idStr))) {
            String newToken = JWT.sign(idStr);
            jedis.set(idStr, newToken);
            Map<String, Object> map = new HashMap<String , Object>();
            map.put("token", newToken);
            return RestResultGenerator.genSuccessResult(map);
        } else {
            return RestResultGenerator.genErrorResult("token无效");
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
                                   @RequestParam (value = "email", required = false) String email,
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
        user.setEmail(email);
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
