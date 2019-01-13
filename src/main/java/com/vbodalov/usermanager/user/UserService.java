package com.vbodalov.usermanager.user;

import com.vbodalov.usermanager.common.exceptionshandling.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    User create(User user) {
        if (user == null) {
            throw new IllegalArgumentException("user argument is null!");
        }
        if (user.getId() != null) {
            throw new IllegalArgumentException(
                    "User ID is not null! Maybe you should use update method instead.");
        }

        return userRepository.save(user);
    }

    UserCredentials updateCredentials(long userId, UserCredentials userCredentials)
            throws EntityNotFoundException {

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

        User user = userRepository.findById(userId).orElseThrow(() ->
                new EntityNotFoundException(User.class, "id", String.valueOf(userId)));
        user.setUserName(userCredentials.getUserName());
        user.setPassword(userCredentials.getPassword());
        User updatedUser = userRepository.save(user);

        return modelMapper.map(updatedUser, UserCredentials.class);
    }

    boolean toggleActive(long userId) throws EntityNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new EntityNotFoundException(User.class, "id", String.valueOf(userId)));
        user.setActive(!user.isActive());
        User updatedUser = userRepository.save(user);
        return updatedUser.isActive();
    }

    Collection<User> findAll() {
        return StreamSupport
                .stream(userRepository.findAll().spliterator(), false)
                .collect(toList());

    }

    User findByUserNameAndPassword(String userName, String password)
            throws EntityNotFoundException {

        if (userName == null || userName.isEmpty()) {
            throw new IllegalArgumentException("userName argument is null or empty!");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("password argument is null or empty!");
        }

        User user = userRepository.findByUserNameAndPassword(userName, password);
        if (user == null) {
            throw new EntityNotFoundException(
                    User.class,
                    "userName", userName,
                    "password", password);
        }
        return user;
    }
}
