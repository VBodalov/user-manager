package com.vbodalov.usermanager.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

@Service
class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    User findByUserNameAndPassword(String userName, String password) {
        if (userName == null || userName.isEmpty()) {
            throw new IllegalArgumentException("userName argument is null or empty!");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("password argument is null or empty!");
        }

        return userRepository.findByUserNameAndPassword(userName, password);
    }

    Collection<User> findAll() {
        try {
            return StreamSupport
                    .stream(userRepository.findAll().spliterator(), false)
                    .collect(toList());
        } catch (Exception e) {
            return null;
        }
    }

    boolean blockUser() {
        return false;
    }

    User save(User user) {
        if (user == null) {
            throw new IllegalArgumentException("user argument is null!");
        }
        return userRepository.save(user);
    }
}
