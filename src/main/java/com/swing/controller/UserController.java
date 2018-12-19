package com.swing.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
@RequestMapping("/user")
public class UserController {

    @RequestMapping(path = "/register",method = RequestMethod.POST)
    public String register(@RequestParam(value = "email", required = false) String password, @RequestParam(value = "password", required = true) String email) {
        return password + email;
    }




}
