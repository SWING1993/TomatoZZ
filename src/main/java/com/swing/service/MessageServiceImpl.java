package com.swing.service;

import com.swing.dao.MessageDao;
import com.swing.entity.Message;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("messageService")
public class MessageServiceImpl implements MessageService {

    @Resource
    private MessageDao messageDao;

    public void addMsg(Message message) {
        messageDao.addMsg(message);
    }

    public List<Message> findMsgByUid(int uid) {
        return messageDao.findMsgByUid(uid);
    }
}
