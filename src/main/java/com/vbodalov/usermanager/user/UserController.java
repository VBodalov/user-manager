package com.vbodalov.usermanager.user;

import com.vbodalov.usermanager.common.exceptionshandling.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
public class UserController {

    private final ModelMapper modelMapper;
    private final UserService userService;

    @Autowired
    public UserController(ModelMapper modelMapper, UserService userService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @PostMapping(value = "/user/create", produces = APPLICATION_JSON_UTF8_VALUE)
    public User createNewUser(@RequestBody UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        return userService.create(user);
    }

    @PostMapping(value = "/user/updateCredentials/{userId}", produces = APPLICATION_JSON_UTF8_VALUE)
    public UserCredentials updateCredentials(
            @PathVariable("userId") long userId,
            @RequestBody UserCredentials userCredentials
    ) throws EntityNotFoundException {
        return userService.updateCredentials(userId, userCredentials);
    }

    @PostMapping(value = "/user/toggleActive/{userId}")
    public boolean toggleActive(@PathVariable("userId") long userId)
            throws EntityNotFoundException {
        return userService.toggleActive(userId);
    }

    @GetMapping(value = "/user/find-all", produces = APPLICATION_JSON_UTF8_VALUE)
    public Collection<User> findAll() {
        return userService.findAll();
    }

    @PostMapping(value = "/user/findByUserNameAndPassword", produces = APPLICATION_JSON_UTF8_VALUE)
    public User findByUserNameAndPassword(@RequestBody UserCredentials userCredentials)
            throws EntityNotFoundException {
        return userService.findByUserNameAndPassword(
                userCredentials.getUserName(),
                userCredentials.getPassword());
    }
}
