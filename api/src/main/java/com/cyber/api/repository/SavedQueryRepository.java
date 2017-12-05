package com.cyber.api.repository;

import com.cyber.api.models.SavedQuery;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface SavedQueryRepository extends CrudRepository<SavedQuery, Integer> {

    public SavedQuery findBySql(String sql);

    public Page<SavedQuery> findByNameIsNotNull(Pageable page);

    public Page<SavedQuery> findAll(Pageable page);

    public Page<SavedQuery> findByNameIsNull(Pageable pageable);

    public SavedQuery findByName(String name);

    @Query(value = "select * from saved_queries where sql = :sql and name is null limit 1", nativeQuery = true)
    public SavedQuery findBySqlAndNameIsNull(@Param("sql") String sql);

    @Query(value = "select * from saved_queries where name = :name and user_id = :user_id limit 1", nativeQuery = true)
    public SavedQuery findByNameAndUserId(@Param("name") String name, @Param("user_id") Integer userId);

    public List<SavedQuery> findByIntervalQuery(String interval);

}
