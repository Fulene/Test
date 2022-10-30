package com.synox.test.repository;

import com.synox.test.model.Dishe;
import com.synox.test.model.Ingredient;
import com.synox.test.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class UserRepository {

    private final List<User> users = new ArrayList<>();

    public UserRepository() {
        fillUsersList();
    }

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

    private void fillUsersList() {
//        Dishe pizza = new Dishe("Pizza", Arrays.asList(new Ingredient("pâte"), new Ingredient("jambon"), new Ingredient("merguez")));
//        Dishe burger = new Dishe("Burger", Arrays.asList(new Ingredient("pain"), new Ingredient("steak"), new Ingredient("fromage")));
//        Dishe tagine = new Dishe("Tagine", Arrays.asList(new Ingredient("agneau"), new Ingredient("patates")));
//        Dishe autre = new Dishe("Autre", Arrays.asList(new Ingredient("autre"), new Ingredient("autre"), new Ingredient("autre"), new Ingredient("autre")));
//
//        User mehdi = new User(1, "Mehdi", "Hamerlaine", Arrays.asList(pizza, burger, tagine));
//        User narj = new User(1, "Narj", "Tona", Arrays.asList(burger, tagine));
//        User toto = new User(1, "Toto", "Titi", Arrays.asList(autre, pizza));
//
//        users.add(mehdi);
//        users.add(narj);
//        users.add(toto);
    }

}
