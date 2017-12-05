package com.cyber.account.repository;

import com.cyber.account.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

    User findByUsername(String login);
}
