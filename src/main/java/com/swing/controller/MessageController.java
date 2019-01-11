package com.swing.controller;

import com.swing.entity.Message;
import com.swing.service.MessageService;
import com.swing.utils.JWT;
import com.swing.utils.RestResult;
import com.swing.utils.RestResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@ResponseBody
@Controller
@RequestMapping("/msg")
public class MessageController {

    @Resource
    private MessageService messageService;

    @RequestMapping(path = "/send",method = RequestMethod.GET)
    public RestResult<String> send(@RequestParam(value = "token") String token,
                                   @RequestParam(value = "title", required = false) String title,
                                   @RequestParam(value = "body", required = false) String body) {
        System.out.println("发送消息" );
        String uidString = JWT.unsign(token, String.class);
        Message message = new Message();
        System.out.println("uid = " + uidString);
        int uid = Integer.valueOf(uidString);
        message.setUid(uid);
        message.setTitle(title);
        message.setBody(body);
        message.setCreated(new Date().getTime());
        messageService.addMsg(message);
        return RestResultGenerator.genSuccessResult();
    }

    @RequestMapping(path = "/find",method = RequestMethod.GET)
    public RestResult<List> findByUid(@RequestParam(value = "token") String token) {
        String uidString = JWT.unsign(token, String.class);
        System.out.println("uid" + uidString);
        int uid = Integer.valueOf(uidString);
        List list = messageService.findMsgByUid(uid);
        return RestResultGenerator.genSuccessResult(list);
    }
}
