package com.cyber.api.repository;

import com.cyber.api.models.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface OAuthAccessTokenRepository extends CrudRepository<Activity, String> {

    public Page<Activity> findAll(Pageable pageable);
}
