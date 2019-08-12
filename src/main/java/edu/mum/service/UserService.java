package edu.mum.service;

import edu.mum.domain.Message;
import edu.mum.domain.User;

import java.util.List;

public interface UserService {
    public User save(User user);

    public List<User> getUsers();

    public User getUserById(Long id);

    public User findByEmail(String email);

    public List<Message> getLast5UnreadNotifyMessageByUserEmail(String email);
}
