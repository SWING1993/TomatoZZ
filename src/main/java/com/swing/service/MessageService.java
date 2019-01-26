package com.swing.service;

import com.swing.entity.Message;
import java.util.List;

public interface MessageService {

    public void addMsg(Message message);

    public List<Message> findMsgByUid(int uid);
}
