package com.cyber.api.repository;

import com.cyber.api.models.Authority;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends CrudRepository<Authority, Integer> {

}
