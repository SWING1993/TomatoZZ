package com.swing.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MessageController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/hey")
    public String hey() {
        return "xx";
    }

}
