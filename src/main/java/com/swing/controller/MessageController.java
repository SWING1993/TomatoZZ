package com.swing.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MessageController {

    @RequestMapping("/message/go")
    public String goTest() {
        return "reach";
    }

    @RequestMapping("/")
    public String index() {
        return "index";
    }



}
