package com.vbodalov.usermanager.user;

import org.springframework.data.repository.CrudRepository;

interface UserRepository extends CrudRepository<User, Long> {

    User findByUserNameAndPassword(String userName, String password);
}
