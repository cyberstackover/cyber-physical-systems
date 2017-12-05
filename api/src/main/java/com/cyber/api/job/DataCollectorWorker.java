package com.cyber.api.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.cyber.api.models.Collector;
import com.cyber.api.services.SavedQueryService;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.concurrent.CountDownLatch;
import org.springframework.jdbc.core.JdbcTemplate;

public class DataCollectorWorker {

    @Autowired
    private JdbcTemplate hiveTemplateIngest;

    private static final Logger LOGGER = LoggerFactory.getLogger(DataCollectorWorker.class);

    public static final int BUCKET_SIZE = 1000;

    private CountDownLatch latch;

    private LeakyBucket bucket;

    @Autowired
    public DataCollectorWorker(CountDownLatch latch) {
        this.latch = latch;
        bucket = new LeakyBucket(BUCKET_SIZE);
    }

    public synchronized void receiveData(String message) {

        try {
            Collector data = Collector.fromJson(message);

            String key = data.getDatabase() + "." + data.getTable();

            List<Map> dataCollected = bucket.fillOrConsume(key, data.getData());

            if (dataCollected != null) {
                insert(data.getDatabase(), data.getTable(), dataCollected);
            }

            LOGGER.info("Received <" + data.getDatabase() + ">" + data.getTable());
            latch.countDown();
        } catch (IOException ex) {
            LOGGER.info(ex.getMessage());
        }
    }

    /**
     * insert data into hive table
     *
     * @param database
     * @param table
     * @param collections
     */
    public void insert(String database, String table, List<Map> collections) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ");
        sql.append(database);
        sql.append(".");
        sql.append(table);
        sql.append(" VALUES");

        boolean commaColumn = false;
        for (Map collection : collections) {

            if (commaColumn) {
                sql.append(",");
            }

            commaColumn = true;
            boolean needComma = false;
            sql.append("(");
            for (Object column : collection.keySet()) {
                if (needComma) {
                    sql.append(",");
                }

                needComma = true;
                sql.append("'");
                sql.append(collection.get(column));
                sql.append("'");
            }
            sql.append(")");
        }

        hiveTemplateIngest.execute(sql.toString());
    }

}
