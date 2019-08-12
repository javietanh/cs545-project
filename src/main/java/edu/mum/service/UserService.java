package edu.mum.service;

import edu.mum.domain.Message;
import edu.mum.domain.User;

import java.util.List;

public interface UserService {
    User save(User user);

    User findByEmail(String email);

    List<Message> getLast5UnreadNotifyMessageByUserEmail(String email);
}
