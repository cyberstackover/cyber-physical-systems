package com.cyber.api.repository;

import com.cyber.api.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

	User findByUsername(String login);
        
        public Page<User> findAll(Pageable pageable);
}
