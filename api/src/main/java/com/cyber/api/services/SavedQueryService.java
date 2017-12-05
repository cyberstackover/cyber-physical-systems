package com.cyber.api.services;

import com.cyber.api.job.DataCollectorWorker;
import com.cyber.api.models.SavedQuery;
import com.cyber.api.models.QueryResult;
import com.cyber.api.models.User;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.cyber.api.repository.SavedQueryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class SavedQueryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SavedQueryService.class);

    @Autowired
    private SavedQueryRepository savedQueryRepository;

    @Autowired
    private UserService userService;

    @Autowired
    protected JdbcTemplate hiveTemplate;

    public QueryResult execute(SavedQuery query) {
        SavedQuery existing = savedQueryRepository.findBySqlAndNameIsNull(query.getSql());
        if (existing == null) {
            existing = query;
        }
        storeQuery(existing);
        QueryResult result = executeQueryForResult(query);
        return result;
    }

    public void storeQuery(SavedQuery query) {
        User user = userService.getAuthenticatedUser();

        if (query.getName() != null) {
            SavedQuery existing = savedQueryRepository.findByNameAndUserId(query.getName(), user.getId());
            if (existing != null) {
                existing.setSql(query.getSql());
                existing.setIntervalQuery(query.getIntervalQuery());
                query = existing;
            }
        }

        query.setUser(user);
        savedQueryRepository.save(query);
    }

    public QueryResult executeQueryForResult(SavedQuery query) {
        QueryResult queryResult = new QueryResult();
        String lowerSql = query.getSql().toLowerCase();
        queryResult.setStarttime(System.currentTimeMillis());

        if (lowerSql.contains("select") || lowerSql.startsWith("show") || lowerSql.startsWith("describe")) {
            List<Map<String, Object>> result = hiveTemplate.queryForList(query.getSql());
            queryResult.setResult(result);
        } else {
            hiveTemplate.execute(query.getSql());
        }

        queryResult.setEndtime(System.currentTimeMillis());

        return queryResult;
    }

}
