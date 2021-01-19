package com.synox.test.repository;

import com.synox.test.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class UserRepository {

    private List<User> users = new ArrayList<>(
            Arrays.asList(new User("Mehdi", "Hamerlaine", 27, "90kg", "1.70m"),
            new User("Narj", "Tona", 26, "80kg", "1.60m"),
            new User("Toto", "tata", 7, "90kg", "1.70m"))
    );

    public List<User> getUsers() {
        return users;
    }

    public User getUserById(int id) {
        return users.get(id);
    }

    public User save(User user) {
        users.add(user);
        return user;
    }

}
