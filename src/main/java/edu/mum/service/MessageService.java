package edu.mum.service;

import edu.mum.domain.Message;

import java.util.List;

public interface MessageService {
    public Message saveMessage(Message message);

    public List<Message> getMessages();

    public Message getMessageById(Long id);
}
