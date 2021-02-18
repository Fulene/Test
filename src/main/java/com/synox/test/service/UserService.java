package com.synox.test.service;

import com.synox.test.model.User;
import com.synox.test.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    final UserRepository userRepository;

    public List<User> getUsers() {
        return userRepository.getUsers();
    }

    public User getUserById(int id) {
        return userRepository.getUserById(id);
    }
    public User saveUser(User user) {
        return userRepository.save(user);
    }

}
