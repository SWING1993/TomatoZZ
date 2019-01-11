package com.swing.dao;

import com.swing.entity.Message;

import java.util.List;

public interface MessageDao {

    void addMsg(Message message);

    List<Message> findMsgByUid(int uid);

}
