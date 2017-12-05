package com.cyber.api.repository;

import com.cyber.api.models.QueryResult;
import com.cyber.api.models.SavedQuery;
import java.util.Date;
import java.util.List;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface QueryResultRepository extends CrudRepository<QueryResult, Integer> {

    @Query(value = "select * from query_result where query_id = :query_id limit 1", nativeQuery = true)
    public QueryResult findOneByQuery(@Param("query_id") String queryId);

    @Query(value = "select * from query_result where query_id = :query_id", nativeQuery = true)
    public List<QueryResult> findAllByQuery(@Param("query_id") String queryId);

    public Page<QueryResult> findByQuery(SavedQuery query, Pageable page);

    public QueryResult findFirstByQuery(SavedQuery queryId);

    List<QueryResult> findByQueryAndCreatedBetween(SavedQuery query, Date start, Date end);
}
