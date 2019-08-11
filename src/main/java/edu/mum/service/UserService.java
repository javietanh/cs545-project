package edu.mum.service;

import edu.mum.domain.User;

import java.util.List;

public interface UserService {
    public User saveUser(User user);

    public List<User> getUsers();

    public User getUserById(Long id);
}
