package com.cyber.api.job;

import com.cyber.api.models.QueryResult;
import com.cyber.api.models.SavedQuery;
import com.cyber.api.repository.QueryResultRepository;
import com.cyber.api.utils.Interval;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.cyber.api.repository.SavedQueryRepository;
import java.util.ArrayList;

@Component
@EnableScheduling
public class QueryScheduler {

    @Autowired
    private SavedQueryRepository savedQueryRepository;

    @Autowired
    private QueryResultRepository queryResultRepository;

    @Autowired
    protected JdbcTemplate hiveTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(DataCollectorWorker.class);

    @Scheduled(cron = "0 0 * * * *")
    //@Scheduled(fixedRate = 50000)
    public void runQueryHourly() {
        runningQuery(Interval.HOURLY);
    }

    @Scheduled(cron = "0 0 12 * * *")
    public void runQueryDaily() {
        runningQuery(Interval.DAILY);
    }

    @Scheduled(cron = "0 0 12 * * FRI")
    public void runQueryWeekly() {
        runningQuery(Interval.WEEKLY);
    }

    @Scheduled(cron = "0 0 12 28 * ?")
    public void runQueryMontly() {
        runningQuery(Interval.MONTHLY);
    }

    @Scheduled(cron = "0 0 12 28 12 ?")
    public void runQueryAnnualy() {
        runningQuery(Interval.ANNUALLY);
    }

    private void runningQuery(String interval) {
        List<SavedQuery> queries = savedQueryRepository.findByIntervalQuery(interval);

        for (SavedQuery query : queries) {
            System.out.println(query.getSql());
            long start = System.currentTimeMillis();
            List<Map<String, Object>> result = hiveTemplate.queryForList(query.getSql());
            long end = System.currentTimeMillis();

            QueryResult queryResult = queryResultRepository.findFirstByQuery(query);

            if (queryResult != null && !query.isIsReplaced()) {
                queryResultRepository.delete(queryResult.getId());
            }
            
            if (queryResult == null) {
                queryResult = new QueryResult();
            }

            queryResult.setResult(result);
            queryResult.setQuery(query);
            queryResult.setStarttime(start);
            queryResult.setEndtime(end);
            List<QueryResult> qResult = new ArrayList<>();
            qResult.add(queryResult);
            query.setQueryResult(qResult);

            savedQueryRepository.save(query);
        }
    }
}
