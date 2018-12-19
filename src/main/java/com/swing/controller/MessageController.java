package com.swing.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @ResponseBody
    @RequestMapping(path = "/print",method = RequestMethod.GET)
    public String printHello() {
        return "xx";
    }

}
