package edu.mum.service.impl;

import edu.mum.domain.User;
import edu.mum.respository.UserRepository;
import edu.mum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User save(User user) {
        String hashPassword = bCryptPasswordEncoder.encode(user.getPassword());
        // change to hashed password
        user.setPassword(hashPassword);
        // persisted user to db.
        return userRepository.save(user);
    }
}
