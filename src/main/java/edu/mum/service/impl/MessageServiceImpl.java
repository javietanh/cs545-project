package edu.mum.service.impl;

import edu.mum.repository.MessageRepository;
import edu.mum.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public void setMessageRead(Long id) {
        messageRepository.setMessageRead(id);
    }
}
