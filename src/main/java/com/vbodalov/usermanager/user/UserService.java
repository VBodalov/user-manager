package com.vbodalov.usermanager.user;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

@Service
class UserService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Autowired
    public UserService(ModelMapper modelMapper, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    User save(User user) {
        if (user == null) {
            throw new IllegalArgumentException("user argument is null!");
        }
        return userRepository.save(user);
    }

    UserCredentials updateCredentials(long userId, UserCredentials userCredentials) {
        if (userCredentials == null) {
            throw new IllegalArgumentException("userCredentials argument is null!");
        }
        if (userCredentials.getUserName() == null
                || userCredentials.getUserName().isEmpty()
                || userCredentials.getPassword() == null
                || userCredentials.getPassword().isEmpty()
        ) {
            throw new IllegalArgumentException("User name or password is null or empty!");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Can't find userD!"));
        user.setUserName(userCredentials.getUserName());
        user.setPassword(userCredentials.getPassword());
        User updatedUser = userRepository.save(user);

        return modelMapper.map(updatedUser, UserCredentials.class);
    }

    boolean toggleBlocking(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Can't find user!"));
        user.setBlocked(!user.isBlocked());
        User updatedUser = userRepository.save(user);
        return updatedUser.isBlocked();
    }

    Collection<User> findAll() {
        return StreamSupport
                .stream(userRepository.findAll().spliterator(), false)
                .collect(toList());

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
}
