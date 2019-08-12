package edu.mum.service;

import edu.mum.domain.Message;
import edu.mum.domain.User;

import java.util.List;

public interface UserService {

    Boolean validatePassword(String password, String hashedPassword);

    User save(User user);

    User changePassword(String newPassword, User user);

    User findByEmail(String email);

    List<Message> getLast5UnreadNotifyMessageByUserEmail(String email);
}
