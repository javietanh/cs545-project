package edu.mum.service.impl;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import edu.mum.config.MailConfig;
import edu.mum.domain.Message;
import edu.mum.domain.User;
import edu.mum.repository.MessageRepository;
import edu.mum.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    private MailConfig mailConfig;

    @Override
    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public List<Message> getMessages() {
        return (List<Message>) messageRepository.findAll();
    }

    @Override
    public Message getMessageById(Long id) {
        return messageRepository.findById(id).get();
    }

    @Override
    public void delete(Message message) {
        messageRepository.delete(message);
    }

    @Override
    public void setMessageRead(Long id) {
        Message msg = messageRepository.findById(id).get();
        if(msg != null){
            msg.setRead(true);
            messageRepository.save(msg);
        }
    }

    @Override
    public void sendMessageToUser(User toUser, String content) {
        Message msg = new Message();
        msg.setContent(content);
        msg.setReceivedDate(LocalDateTime.now());
        msg.setUser(toUser);
        msg.setRead(false);
        messageRepository.save(msg);
    }

    @Override
    public JsonNode sendEmail(String from, String to, String subject, String content) throws UnirestException {
        HttpResponse<JsonNode> request = Unirest.post(mailConfig.getMailMessageUrl())
                .basicAuth("api", mailConfig.getApiKey())
                .queryString("from", from == null ? mailConfig.getNoReplyEmail() : from)
                .queryString("to", to)
                .queryString("subject", subject)
                .queryString("text", content)
                .asJson();
        return request.getBody();
    }
}
