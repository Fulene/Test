package com.synox.test.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.synox.test.model.User;
import com.synox.test.service.UserService;
import com.synox.test.view.Views;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/test")
public class TestRestController {

    @Value("${test.firstname}")
    private String testFn;

    private final UserService userService;

    @GetMapping("/test-fn")
    public String testFn() {
        return testFn;
    }

    @GetMapping()
    public List<User> getUsersWithoutJsonView() {
        return userService.getUsers();
    }

    @JsonView(Views.WithRestrictionView.class)
    @GetMapping("/users")
    public List<User> getUsersWithRestriction() {
        return userService.getUsers();
    }

    @JsonView(Views.WithoutRestrictionView.class)
    @GetMapping("/usersAllDetails")
    public List<User> getUsersWithoutRestriction() {
        return userService.getUsers();
    }

    @GetMapping("/usersByIds/{ids}")
    public List<Integer> getUserById(@PathVariable List<Integer> ids) {
        return ids;
    }

    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.saveUser(user));
    }

}
