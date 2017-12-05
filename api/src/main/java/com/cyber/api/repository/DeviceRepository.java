package com.cyber.api.repository;

import com.cyber.api.models.Device;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface DeviceRepository extends CrudRepository<Device, String> {

    public Page<Device> findAll(Pageable pageable);

    @Query(value = "select * from oauth_client_details where authorized_grant_types like '%client_credentials%'", nativeQuery = true)
    public List<Device> findByClientCredentials();

}
