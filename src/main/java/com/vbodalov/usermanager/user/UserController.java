package com.vbodalov.usermanager.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/user/findByUserNameAndPassword", produces = APPLICATION_JSON_UTF8_VALUE)
    public User findByUserNameAndPassword(@RequestBody UserCredentials userCredentials) {
        return userService.findByUserNameAndPassword(userCredentials.getUserName(), userCredentials.getPassword());
    }

    @GetMapping(value = "/user/find-all", produces = APPLICATION_JSON_UTF8_VALUE)
    public Collection<User> findAll() {
        return userService.findAll();
    }

    @PostMapping(value = "/user/create", produces = APPLICATION_JSON_UTF8_VALUE)
    public User createNewUser(@RequestBody User user) {
        return userService.save(user);
    }
}
