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

    Collection<User> findAll() {
        try {
            return StreamSupport
                    .stream(userRepository.findAll().spliterator(), false)
                    .collect(toList());
        } catch (Exception e) {
            return null;
        }
    }

    User findByUserNameAndPassword(String userName, String password) {
        return null;
    }

    boolean blockUser() {
        return false;
    }

    User save(User user) {
        return null;
    }
}
