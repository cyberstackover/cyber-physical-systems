package com.cyber.api.services;

import com.cyber.api.models.QueryResult;
import com.cyber.api.models.SavedQuery;
import com.cyber.api.repository.QueryResultRepository;
import com.cyber.api.repository.SavedQueryRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QueryResultService {
    
    
    @Autowired
    private QueryResultRepository queryResultRepository;

    @Autowired
    private SavedQueryRepository savedQueryRepository;
    
    public List<Map<String, Object>> findResultByDate(int queryId, Date start, Date end){

        List<Map<String, Object>> appendedResult = new ArrayList<>();
        SavedQuery query = savedQueryRepository.findOne(queryId);
       
        List<QueryResult> result = queryResultRepository.findByQueryAndCreatedBetween(query, start, end);

        for (QueryResult queryResult : result) {
            List all = queryResult.getResult();
            appendedResult.addAll(all);
        }
        
        return appendedResult;
    }
    
}
